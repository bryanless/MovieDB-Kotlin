package com.ss.moviedb_kotlin.ui.now_playing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ss.moviedb_kotlin.model.movies.NowPlayingMovie
import com.ss.moviedb_kotlin.data.repository.NowPlayingRepository
import kotlinx.coroutines.flow.Flow

class NowPlayingViewModel(private val nowPlayingRepository: NowPlayingRepository) : ViewModel() {
    val pagingDataFlow: Flow<PagingData<NowPlayingMovie>>

    init {
        pagingDataFlow = getNowPlayingMovies().cachedIn(viewModelScope)
    }

    // * Get now playing movies
    private fun getNowPlayingMovies(): Flow<PagingData<NowPlayingMovie>> =
        nowPlayingRepository.getNowPlayingMoviesStream()
}