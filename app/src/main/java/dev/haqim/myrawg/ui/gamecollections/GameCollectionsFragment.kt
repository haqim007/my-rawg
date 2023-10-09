package dev.haqim.myrawg.ui.gamecollections

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import dev.haqim.myrawg.R
import dev.haqim.myrawg.databinding.FragmentGameCollectionsBinding
import dev.haqim.myrawg.databinding.FragmentGamesBinding
import dev.haqim.myrawg.domain.model.GamesListItem
import dev.haqim.myrawg.ui.common.GamesListAdapter
import dev.haqim.myrawg.ui.common.GamesListListener
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameCollectionsFragment : Fragment() {
    
    private lateinit var binding: FragmentGameCollectionsBinding
    private val viewModel: GameCollectionsListVM by viewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentGameCollectionsBinding.inflate(layoutInflater, container, false)
        val adapter = setupAdapter()
        searchSetup(adapter)
        binding.btnUp.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }

    private fun setupAdapter(): GamesListAdapter {
        val adapter = GamesListAdapter(object : GamesListListener() {
            override fun onClick(game: GamesListItem) {
                findNavController().navigate(
                    GameCollectionsFragmentDirections.actionGameCollectionsFragmentToGameDetailFragment(game.id)
                )
            }
        })
        with(binding){
            rvGamesList.adapter = adapter
            
            viewLifecycleOwner.lifecycleScope.launch { 
                viewModel.pagingFlow.collect{
                    adapter.submitData(it)
                }
            }
            
            viewLifecycleOwner.lifecycleScope.launch {

                adapter.addLoadStateListener {
                    if (it.refresh is LoadState.NotLoading){
                        rvGamesList.smoothScrollToPosition(0)
                    }
                    // show loader
                    pbLoader.isVisible = it.refresh is LoadState.Loading && adapter.itemCount == 0

                    val isDataEmpty = it.append.endOfPaginationReached && adapter.itemCount == 0
                    val isRefreshError = it.refresh is LoadState.Error && adapter.itemCount == 0
                    rvGamesList.isVisible = !isDataEmpty && !isRefreshError && !pbLoader.isVisible
                    btnTryAgain.isVisible = (isDataEmpty || isRefreshError) && !pbLoader.isVisible
                    tvErrorMessage.isVisible = (isDataEmpty || isRefreshError) && !pbLoader.isVisible
                    
                    if (isDataEmpty){
                        tvErrorMessage.text = getString(R.string.empty_error_message)
                    }else if(isRefreshError){
                        tvErrorMessage.text = getString(R.string.an_error_occured)
                    }
                }
            }
            
            btnTryAgain.setOnClickListener { 
                adapter.retry()
            }
        }
        return adapter
    }

    private fun searchSetup(adapter: GamesListAdapter) {
        
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(query: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(query: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(query: Editable?) {
                viewModel.searchGames(query.toString())
            }
        })

        val searchFlow = viewModel.state.map { it.search }.distinctUntilChanged()
        
        viewLifecycleOwner.lifecycleScope.launch { 
            searchFlow.collectLatest { 
                adapter.refresh()
                delay(3000)
                binding.etSearch.clearFocus()
            }
        }
    }

}