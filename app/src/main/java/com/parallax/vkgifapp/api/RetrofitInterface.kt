package com.parallax.vkgifapp.api

import com.parallax.vkgifapp.model.GifParams
import com.parallax.vkgifapp.model.MainData
import com.parallax.vkgifapp.util.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitInterface {

    /**
     * Функция получения трендовых гифок
     */
    @GET(Constants.TRENDING + Constants.API_KEY + Constants.LIMIT + Constants.RATING)
    suspend fun getTrendDataGifs(): MainData
    /**
     * Функция получения искомых гифок
     */
    @GET(Constants.SEARCH + Constants.API_KEY + Constants.LIMIT + Constants.OFFSET + Constants.RATING + Constants.lang)
    suspend fun getSearchingDataGifs(@Query("q") searched: String): MainData

}