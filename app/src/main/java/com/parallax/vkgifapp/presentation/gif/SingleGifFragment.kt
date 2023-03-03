package com.parallax.vkgifapp.presentation.gif

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import com.google.gson.Gson
import com.parallax.vkgifapp.R
import com.parallax.vkgifapp.model.GifParams

/**
 * Класс фрагмента, демонстрирующий выбранную гифку
 * Как поля я выбрал размер, айди, название гифки и рейтинг, так как они самые короткие и влезут в текствью
 */
class SingleGifFragment : Fragment() {

    private lateinit var gif: ImageView
    private lateinit var gifUsername: TextView
    private lateinit var gifSize: TextView
    private lateinit var gifRating: TextView
    private lateinit var gifId: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.show_single_gif_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi(view)

        retrieveDataFromMainFragment()
    }

    private fun initUi(view: View) {
        gif = view.findViewById(R.id.single_gif_imageview)
        gifUsername = view.findViewById(R.id.gif_username_textview)
        gifSize = view.findViewById(R.id.gif_size)
        gifRating = view.findViewById(R.id.gif_rating)
        gifId = view.findViewById(R.id.gif_id)
    }

    /**
     * Проставляем дату гифки, забирая данные из переданного бандла
     */
    private fun retrieveDataFromMainFragment() {
        val gson = Gson()
        val data = this.requireArguments().getString("gif")
        val gifParams = gson.fromJson(data, GifParams::class.java)

        val imageLoader = ImageLoader.Builder(this.requireContext().applicationContext)
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()

        gif.load(gifParams.images.imageUrl.url, imageLoader)  //загружаем гифку с помощью коил
        gifUsername.text = gifParams.username
        gifSize.text = gifParams.images.imageUrl.size
        gifRating.text = gifParams.rating
        gifId.text = gifParams.id
    }


}