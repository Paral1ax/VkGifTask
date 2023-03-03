package com.parallax.vkgifapp.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import com.parallax.vkgifapp.R
import com.parallax.vkgifapp.model.GifParams

/**
 * Адаптер для вьюпейджера
 */
class ShowGifsViewpagerAdapter(private val context: Context, private val gifs: List<GifParams>):
    RecyclerView.Adapter<ShowGifsViewpagerAdapter.ShowGifsViewHolder>() {

    var onGifClick: ((GifParams) -> Unit)? = null

    private val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    fun setItemClick(action: (GifParams) -> Unit) {
        this.onGifClick = action
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowGifsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_gif_viewpager2_item, parent, false)
        return ShowGifsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShowGifsViewHolder, position: Int) {

        val gif = gifs[position]

        holder.gif.load(gif.images.imageUrl.url, imageLoader)
    }

    override fun getItemCount(): Int {
        return gifs.size
    }

    inner class ShowGifsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val gif: ImageView = itemView.findViewById(R.id.viewpagerImage)

        init {
            onGifClick?.let {
                gif.setOnClickListener {
                    it(gifs[adapterPosition])
                }
            }
        }
    }
}