package com.parallax.vkgifapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parallax.vkgifapp.api.ApiState
import com.parallax.vkgifapp.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

/**
 * Класс вьюмодели для Мейн фрагмента
 */
class MainFragmentViewModel(private val repository: Repository): ViewModel() {

    val gifTrendList: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)
    val gifSearchedList: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)

    /**
     *Получаем трендовые гифки
     */
    fun getTrendData() = viewModelScope.launch {
        gifTrendList.value = ApiState.Loading  //сначала происходит загрузка
        repository.getTrendData()
            .catch { e ->
                gifTrendList.value = ApiState.Failure(e)  //если произошла ошибка
            }.collect { data ->
                gifTrendList.value = ApiState.Success(data)  //собираем данные
            }
    }
    /**
     *Получаем искомые гифки
     */
    fun getSearchedData(searched: String) = viewModelScope.launch {
        gifSearchedList.value = ApiState.Loading
        repository.getSearchedData(searched)
            .catch { e ->
                gifSearchedList.value = ApiState.Failure(e)
            }.collect { data ->
                gifSearchedList.value = ApiState.Success(data)
            }
    }
}