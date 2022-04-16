package com.ss.moviedb_kotlin.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ss.moviedb_kotlin.data.repository.DetailRepository
import com.ss.moviedb_kotlin.model.movies.DetailMovie
import kotlinx.coroutines.launch

class DetailViewModel(private val detailRepository: DetailRepository) : ViewModel() {
    private val _movie = MutableLiveData<DetailMovie>()
    val movie: LiveData<DetailMovie> get() = _movie

    fun getDetailMovie(movieId: Int) {
        viewModelScope.launch {
            _movie.value = detailRepository.getDetailMovie(movieId)
        }
    }
}