package com.ss.moviedb_kotlin.ui.trending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ss.moviedb_kotlin.data.repository.TrendingRepository
import com.ss.moviedb_kotlin.model.movies.TrendingMovie
import kotlinx.coroutines.flow.Flow

class TrendingViewModel(private val trendingRepository: TrendingRepository) : ViewModel() {
    val pagingDataFlow: Flow<PagingData<TrendingMovie>>

    init {
        pagingDataFlow = getTrendingMovies().cachedIn(viewModelScope)
    }

    // * Get trending movies
    private fun getTrendingMovies(): Flow<PagingData<TrendingMovie>> =
        trendingRepository.getTrendingMoviesStream()
}