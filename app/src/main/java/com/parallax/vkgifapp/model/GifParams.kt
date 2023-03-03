package com.parallax.vkgifapp.model

import com.google.gson.annotations.SerializedName

//Класс параметров гифик
//Добавил разные поля, чтобы потом подумать что лучше показать
data class GifParams(
    val type: String,
    val id: String,
    val url: String,
    val slug: String,
    val username: String,
    val source: String,
    val title: String,
    val rating: String,
    val trending_datetime: String,
    val images: SingleImage
)
//Верхний класс
data class MainData(val data: List<GifParams>)

//Класс для поля original
data class SingleImage(
    @SerializedName("original") val imageUrl: ImageUrl
)
//Обрабатываем ссылку на гифку и размер гифки
data class ImageUrl(val size: String, val url: String)