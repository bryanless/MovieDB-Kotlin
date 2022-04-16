package com.ss.moviedb_kotlin.ui.top_rated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ss.moviedb_kotlin.data.repository.TopRatedRepository

class TopRatedViewModelFactory(private val repository: TopRatedRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TopRatedViewModel::class.java)) {
            return TopRatedViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}