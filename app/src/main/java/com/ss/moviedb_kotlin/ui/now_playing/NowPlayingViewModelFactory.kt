package com.ss.moviedb_kotlin.ui.now_playing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ss.moviedb_kotlin.data.repository.NowPlayingRepository

class NowPlayingViewModelFactory(private val repository: NowPlayingRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NowPlayingViewModel::class.java)) {
            return NowPlayingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}