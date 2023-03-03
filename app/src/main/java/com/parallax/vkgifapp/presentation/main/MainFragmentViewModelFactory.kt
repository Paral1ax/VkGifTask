package com.parallax.vkgifapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.parallax.vkgifapp.model.GifParams
import com.parallax.vkgifapp.repository.Repository

/**
 * Фабрика вью модели основного фрагмента
 */
class MainFragmentViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainFragmentViewModel(repository) as T
    }
}