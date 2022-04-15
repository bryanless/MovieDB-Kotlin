package com.ss.moviedb_kotlin.ui.upcoming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ss.moviedb_kotlin.data.repository.UpcomingRepository

class UpcomingViewModelFactory(private val repository: UpcomingRepository) :
    ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UpcomingViewModel::class.java)) {
                return UpcomingViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}