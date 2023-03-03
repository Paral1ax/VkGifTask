package com.parallax.vkgifapp.api

import android.util.Log
import com.google.gson.GsonBuilder
import com.parallax.vkgifapp.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Инициализация окhttp и ретрофита
 */
object RetrofitClient {

    private const val TIME_OUT: Long = 20

    private val gson = GsonBuilder().setLenient().create()

    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val resp = chain.proceed(chain.request())
            //работа с ответом от апи
            if (resp.code == 200) {
                try {
                    val response = resp.peekBody(1000000).string()
                    println(response)
                    println(resp.request.url)
                } catch (e: Exception) {
                    Log.d("API", "Ошибка обработки Json")
                }
            } else {
                Log.d("API", "Ошибка при обращении к серверу")
            }
            resp
        }.build()

    val retrofit: RetrofitInterface by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(Constants.MAIN_URL)
            .client(okHttpClient)
            .build().create(RetrofitInterface::class.java)
    }

}