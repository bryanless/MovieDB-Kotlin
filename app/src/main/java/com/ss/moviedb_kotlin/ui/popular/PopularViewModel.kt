package com.ss.moviedb_kotlin.ui.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ss.moviedb_kotlin.data.repository.PopularRepository
import com.ss.moviedb_kotlin.model.movies.PopularMovie
import kotlinx.coroutines.flow.Flow

class PopularViewModel(private val popularRepository: PopularRepository) : ViewModel() {
    val pagingDataFlow: Flow<PagingData<PopularMovie>>

    init {
        pagingDataFlow = getPopularMovies().cachedIn(viewModelScope)
    }

    // * Get now playing movies
    private fun getPopularMovies(): Flow<PagingData<PopularMovie>> =
        popularRepository.getPopularMoviesStream()
}