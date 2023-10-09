package dev.haqim.myrawg.ui.gamedetail

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.haqim.myrawg.R
import dev.haqim.myrawg.data.mechanism.Resource
import dev.haqim.myrawg.data.mechanism.ResourceHandler
import dev.haqim.myrawg.data.mechanism.handle
import dev.haqim.myrawg.databinding.FragmentGameDetailBinding
import dev.haqim.myrawg.domain.model.GameDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameDetailFragment : Fragment() {
    private lateinit var binding: FragmentGameDetailBinding
    private val viewModel: GameDetailVM by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentGameDetailBinding.inflate(layoutInflater, container, false)

        val args: GameDetailFragmentArgs by navArgs()
        val id = args.id
        viewModel.getDetail(id)
        
        val detail = viewModel.state.map { it.detail }.distinctUntilChanged()
        viewLifecycleOwner.lifecycleScope.launch { 
            detail.collectLatest {
                with(binding){
                    it.handle(object : ResourceHandler<GameDetail?>{
                        override fun onSuccess(data: GameDetail?) {
                            data?.let {
                                showDetail(data)
                            } ?: run{
                                showError(id, "Detail is empty")
                            }
                        }

                        override fun onError(message: String?, data: GameDetail?) {
                            data?.let {
                                showDetail(data)
                                showSnackbarError(id, message ?: "Unknown error")
                            }?: run {
                                showError(id, message ?: "Unknown error")   
                            }
                            
                        }

                        override fun onAll(resource: Resource<GameDetail?>) {
                            pbLoader.isVisible = resource is Resource.Loading
                            slMainContent.isVisible = !pbLoader.isVisible
                            btnAddCollection.isVisible = !pbLoader.isVisible
                        }
                    })
                }
            }
        }
        
        binding.btnUp.setOnClickListener { 
            findNavController().navigateUp()
        }
        
        binding.btnAddCollection.setOnClickListener {
            viewModel.toggleCollection()
        }
        
        binding.btnToCollection.setOnClickListener { 
            findNavController().navigate(
                GameDetailFragmentDirections.actionGameDetailFragmentToGameCollectionsFragment()
            )
        }
        
        viewLifecycleOwner.lifecycleScope.launch { 
            viewModel.state.map { it.detail }.collectLatest {
                if(it.data?.isCollected == true){
                    val colorStateList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.light_blue))
                    binding.btnAddCollection.backgroundTintList = colorStateList
                    binding.btnAddCollection.setImageResource(R.drawable.ic_list_added)
                }else{
                    val colorStateList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.gray))
                    binding.btnAddCollection.backgroundTintList = colorStateList
                    binding.btnAddCollection.setImageResource(R.drawable.ic_add_list)
                }
            }
        }

        return binding.root
    }

    private fun FragmentGameDetailBinding.showDetail(data: GameDetail) {
        Glide.with(requireContext()).load(data.image)
            .placeholder(R.drawable.ic_downloading)
            .transform(CenterInside(), RoundedCorners(24))
            .into(ivGame)
        tvLastUpdate.text = getString(R.string.last_update_s, data.lastUpdate)
        tvDeveloper.text = data.developers.joinToString(", ")
        tvGameAbout.apply {
            text = data.description
            setOnClickListener { 
                if (this.maxLines == 5){
                    this.maxLines = 100
                }else{
                    this.maxLines = 5
                }
            }
        }
        tvGameName.text = data.title
        tvGameGenres.text = data.genres.joinToString(", ")
        tvMetascore.text = data.metascore.toString()
        tvPlatforms.text = data.platforms.joinToString(", ")
        tvTotalRatings.text = getString(R.string.n_ratings, data.totalRatingsVote)
        tvTopRating.text = data.topRatingsName.replaceFirstChar {
            it.uppercaseChar()
        }
        tvReleaseDate.text = data.releaseDate
        tvPublisher.text = data.publishers.joinToString(", ")
    }

    private fun showError(id: Int, message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.error))
            .setMessage(message)
            .setPositiveButton(getString(R.string.close)) { dialog, which ->
                dialog.dismiss()
                findNavController().popBackStack()
            }
            .setNeutralButton(getString(R.string.try_again)) { dialog, which ->
                viewModel.getDetail(id)
            }
            .show()
    }

    private fun showSnackbarError(id: Int, message: String) {

        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setAction(R.string.try_again) {
                viewModel.getDetail(id)
            }
            .show()
    }

}