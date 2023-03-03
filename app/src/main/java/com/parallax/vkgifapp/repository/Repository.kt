package com.parallax.vkgifapp.repository

import com.parallax.vkgifapp.api.RetrofitClient
import com.parallax.vkgifapp.model.MainData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Репозиторий работы с апи
 */
class Repository {
    fun getTrendData(): Flow<MainData> = flow {
        val r = RetrofitClient.retrofit.getTrendDataGifs()
        emit(r)  //собираем данные из потока
    }.flowOn(Dispatchers.IO)

    fun getSearchedData(searched: String): Flow<MainData> = flow {
        val r = RetrofitClient.retrofit.getSearchingDataGifs(searched)
        emit(r)
    }.flowOn(Dispatchers.IO)

}
