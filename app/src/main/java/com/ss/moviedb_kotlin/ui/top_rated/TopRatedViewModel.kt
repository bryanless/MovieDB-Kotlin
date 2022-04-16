package com.ss.moviedb_kotlin.ui.top_rated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ss.moviedb_kotlin.data.repository.TopRatedRepository
import com.ss.moviedb_kotlin.model.movies.TopRatedMovie
import kotlinx.coroutines.flow.Flow

class TopRatedViewModel(private val topRatedRepository: TopRatedRepository) : ViewModel() {
    val pagingDataFlow: Flow<PagingData<TopRatedMovie>>

    init {
        pagingDataFlow = getTopRatedMovies().cachedIn(viewModelScope)
    }

    // * Get now playing movies
    private fun getTopRatedMovies(): Flow<PagingData<TopRatedMovie>> =
        topRatedRepository.getTopRatedMoviesStream()
}