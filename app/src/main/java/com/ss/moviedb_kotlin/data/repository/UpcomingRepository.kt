package com.ss.moviedb_kotlin.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ss.moviedb_kotlin.data.paging.UpcomingRemoteMediator
import com.ss.moviedb_kotlin.db.remote.MovieDatabase
import com.ss.moviedb_kotlin.model.movies.UpcomingMovie
import com.ss.moviedb_kotlin.network.MovieDbApi
import com.ss.moviedb_kotlin.util.Const
import kotlinx.coroutines.flow.Flow

class UpcomingRepository(
    private val movieDbApi: MovieDbApi,
    private val database: MovieDatabase
) {
    fun getUpcomingMoviesStream(): Flow<PagingData<UpcomingMovie>> {
        // * Directly from network
//        val pagingSourceFactory = { UpcomingPagingSource(movieDbApi) }
        // * Caching from Room
        val pagingSourceFactory = { database.movieDatabaseDao().getUpcomingMovies() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = Const.MOVIEDB_PAGE_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = UpcomingRemoteMediator(
                movieDbApi,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}