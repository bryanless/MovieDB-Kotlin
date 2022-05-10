package com.ss.moviedb_kotlin.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ss.moviedb_kotlin.data.repository.DetailRepository
import com.ss.moviedb_kotlin.model.movies.DetailMovie
import com.ss.moviedb_kotlin.model.movies.RecommendationMovie
import com.ss.moviedb_kotlin.model.movies.TrendingMovie
import com.ss.moviedb_kotlin.model.movies.VideoMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DetailViewModel(private val detailRepository: DetailRepository) : ViewModel() {
    private val _movie = MutableLiveData<DetailMovie>()
    val movie: LiveData<DetailMovie> get() = _movie

    private val _videos = MutableLiveData<List<VideoMovie>>()
    val videos: LiveData<List<VideoMovie>> get() = _videos

    // * Get movie's detail
    fun getDetailMovie(movieId: Int) {
        viewModelScope.launch {
            _movie.value = detailRepository.getDetailMovie(movieId)
        }
    }

    // * Get movie's videos
    fun getVideosMovie(movieId: Int) {
        viewModelScope.launch {
            _videos.value = detailRepository.getVideosMovie(movieId)
        }
    }

    // * Recommendation movies
    val pagingDataFlow: Flow<PagingData<RecommendationMovie>>

    init {
        pagingDataFlow = getRecommendationMovies().cachedIn(viewModelScope)
    }

    // * Get recommendation movies
    private fun getRecommendationMovies(): Flow<PagingData<RecommendationMovie>> =
        detailRepository.getRecommendationMoviesStream()
}