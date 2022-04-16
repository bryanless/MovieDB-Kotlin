package com.ss.moviedb_kotlin.ui.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ss.moviedb_kotlin.data.repository.PopularRepository

class PopularViewModelFactory(private val repository: PopularRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PopularViewModel::class.java)) {
            return PopularViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}