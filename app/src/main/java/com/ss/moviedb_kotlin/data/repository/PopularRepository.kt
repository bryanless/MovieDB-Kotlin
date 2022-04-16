package com.ss.moviedb_kotlin.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ss.moviedb_kotlin.data.paging.PopularRemoteMediator
import com.ss.moviedb_kotlin.db.remote.MovieDatabase
import com.ss.moviedb_kotlin.model.movies.PopularMovie
import com.ss.moviedb_kotlin.network.MovieDbApi
import com.ss.moviedb_kotlin.util.Const
import kotlinx.coroutines.flow.Flow

class PopularRepository(
    private val movieDbApi: MovieDbApi,
    private val database: MovieDatabase
) {
    fun getPopularMoviesStream(): Flow<PagingData<PopularMovie>> {
        // * Directly from network
//        val pagingSourceFactory = { PopularPagingSource(movieDbApi) }
        // * Caching from Room
        val pagingSourceFactory = { database.movieDatabaseDao().getPopularMovies() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = Const.MOVIEDB_PAGE_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = PopularRemoteMediator(
                movieDbApi,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}