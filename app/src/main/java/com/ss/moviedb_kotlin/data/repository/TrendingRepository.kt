package com.ss.moviedb_kotlin.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ss.moviedb_kotlin.data.paging.TrendingRemoteMediator
import com.ss.moviedb_kotlin.db.remote.MovieDatabase
import com.ss.moviedb_kotlin.model.movies.TrendingMovie
import com.ss.moviedb_kotlin.network.MovieDbApi
import com.ss.moviedb_kotlin.util.Const
import kotlinx.coroutines.flow.Flow

class TrendingRepository(
    private val movieDbApi: MovieDbApi,
    private val database: MovieDatabase
) {
    fun getTrendingMoviesStream(): Flow<PagingData<TrendingMovie>> {
        // * Directly from network
//        val pagingSourceFactory = { TrendingPagingSource(movieDbApi) }
        // * Caching from Room
        val pagingSourceFactory = { database.movieDatabaseDao().getTrendingMovies() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = Const.MOVIEDB_PAGE_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = TrendingRemoteMediator(
                movieDbApi,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}