package com.ss.moviedb_kotlin.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ss.moviedb_kotlin.data.paging.NowPlayingRemoteMediator
import com.ss.moviedb_kotlin.db.remote.MovieDatabase
import com.ss.moviedb_kotlin.model.movies.NowPlayingMovie
import com.ss.moviedb_kotlin.network.MovieDbApi
import com.ss.moviedb_kotlin.util.Const
import kotlinx.coroutines.flow.Flow

class NowPlayingRepository(
    private val movieDbApi: MovieDbApi,
    private val database: MovieDatabase
) {
    fun getNowPlayingMoviesStream(): Flow<PagingData<NowPlayingMovie>> {
        // * Directly from network
//        val pagingSourceFactory = { NowPlayingPagingSource(movieDbApi) }
        // * Caching from Room
        val pagingSourceFactory = { database.movieDatabaseDao().getNowPlayingMovies() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = Const.MOVIEDB_PAGE_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = NowPlayingRemoteMediator(
                movieDbApi,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}