package com.ss.moviedb_kotlin.ui.upcoming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ss.moviedb_kotlin.data.repository.UpcomingRepository
import com.ss.moviedb_kotlin.model.movies.UpcomingMovie
import kotlinx.coroutines.flow.Flow

class UpcomingViewModel(private val upcomingRepository: UpcomingRepository) : ViewModel() {
    val pagingDataFlow: Flow<PagingData<UpcomingMovie>>

    init {
        pagingDataFlow = getUpcomingMovies().cachedIn(viewModelScope)
    }

    // * Get upcoming movies
    private fun getUpcomingMovies(): Flow<PagingData<UpcomingMovie>> =
        upcomingRepository.getUpcomingMoviesStream()
}