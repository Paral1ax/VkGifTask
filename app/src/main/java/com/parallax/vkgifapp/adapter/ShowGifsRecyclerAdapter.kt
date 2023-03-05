package com.parallax.vkgifapp.adapter

import android.content.Context
import android.media.Image
import android.os.Build.VERSION.SDK_INT
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import coil.request.ImageRequest
import com.parallax.vkgifapp.R
import com.parallax.vkgifapp.model.GifParams

/**
 * класс адаптер для ресайклервью
 */
class ShowGifsRecyclerAdapter(private val context: Context, private var gifs: List<GifParams>): RecyclerView.Adapter<ShowGifsRecyclerAdapter.ShowGifsViewHolder>() {

    var onGifClick: ((GifParams) -> Unit)? = null  //Все это сделано для удобного получения полей выбранной гифки в мейн фрагменте

    private val imageLoader = ImageLoader.Builder(context)  //имедж лоадер для настройки под гифки библиотеки Coil
        .components {
            if (SDK_INT >= 28) {
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_gif_recycler_item, parent, false)
        return ShowGifsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShowGifsViewHolder, position: Int) {

        val gif = gifs[position]

        holder.gif.load(gif.images.imageUrl.url, imageLoader)  //загружаем гифки с помощью коил
        holder.gifUsername.text = gif.username
    }

    override fun getItemCount(): Int {
        return gifs.size
    }

    inner class ShowGifsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val gif: ImageView = itemView.findViewById(R.id.recyclerviewImage)
        val gifUsername: TextView = itemView.findViewById(R.id.main_gif_username)

        init {
            onGifClick?.let {
                gif.setOnClickListener {
                    it(gifs[adapterPosition])
                }
            }
        }
    }
}