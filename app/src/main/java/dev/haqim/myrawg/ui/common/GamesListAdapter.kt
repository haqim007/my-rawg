package dev.haqim.myrawg.ui.common

import android.content.res.Resources
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import dev.haqim.myrawg.R
import dev.haqim.myrawg.databinding.LayoutGameItemBinding
import dev.haqim.myrawg.domain.model.GamesListItem

class GamesListAdapter(
    private val listener: GamesListListener
) : PagingDataAdapter<GamesListItem, RecyclerView.ViewHolder>(DIFF_CALLBACK){

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).onBind(getItem(position), listener)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder.onCreate(parent)
    }

    class ViewHolder private constructor(
        private val binding: LayoutGameItemBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun onBind(game: GamesListItem?, listener: GamesListListener){
            game?.let {
                    
                binding.tvGameName.text = it.title
                binding.tvGameGenres.text = it.genres.joinToString(", ")
                binding.tvRating.text = itemView.context.getString(R.string.per_5, it.rating)
                Glide.with(itemView.context).load(it.image)
                    .placeholder(R.drawable.ic_downloading)
                    .transform(CenterInside(), RoundedCorners(24))
                    .into(binding.ivGame)
                val platforms = if(it.parentPlatforms.size >= 5){
                    it.parentPlatforms.subList(0, 4)
                }else{
                    it.parentPlatforms
                }
                binding.llPlatforms.removeAllViews()
                platforms.forEach {
                    val drawableRes = when(it.lowercase()){
                        "playstation" -> R.drawable.playstation
                        "xbox" -> R.drawable.xbox
                        "linux" -> R.drawable.linux
                        "android" -> R.drawable.android
                        "mac" -> R.drawable.mac
                        "ios" -> R.drawable.ios
                        "nintendo" -> R.drawable.nintendo
                        else -> {
                            R.drawable.windows
                        }
                    }
                    // Create a new ImageView
                    val imageView = ImageView(itemView.context).apply {
                        // Set layout parameters
                        layoutParams = LinearLayout.LayoutParams(
                            dpToPx(20),  // width
                            dpToPx(20)   // height
                        ).apply {
                            // Set margins
                            leftMargin = dpToPx(4)
                            rightMargin = dpToPx(4)
                        }

                        // Set an image to the ImageView
                        setImageDrawable(ContextCompat.getDrawable(context, drawableRes))

                        // Set the tint color
                        setColorFilter(ContextCompat.getColor(context, R.color.white))

                    }

                    // Add the ImageView to the LinearLayout
                    binding.llPlatforms.addView(imageView)
                }
                
                if((it.parentPlatforms.size - 5) > 0){
                    // Create a new TextView
                    val textView = TextView(itemView.context).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,  // width
                            LinearLayout.LayoutParams.WRAP_CONTENT   // height
                        )

                        // Set text
                        text = "+${(it.parentPlatforms.size - 5)}"

                        // Set text size
                        textSize = 15f

                        // Set text style
                        setTypeface(typeface, Typeface.BOLD)

                        // Set text color
                        setTextColor(ContextCompat.getColor(context, R.color.white))
                    }

                    // Add the TextView to the LinearLayout
                    binding.llPlatforms.addView(textView)
                }




                binding.root.setOnClickListener {
                    listener.onClick(game)
                }
            }
        }

        companion object{
            fun onCreate(parent: ViewGroup): ViewHolder {
                val itemView = LayoutGameItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(itemView)
            }
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GamesListItem>(){
            override fun areItemsTheSame(oldItem: GamesListItem, newItem: GamesListItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: GamesListItem, newItem: GamesListItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}

abstract class GamesListListener{
    abstract fun onClick(game: GamesListItem)
}

fun dpToPx(dp: Int): Int {
    val density = Resources.getSystem().displayMetrics.density
    return (dp * density + 0.5f).toInt()
}
