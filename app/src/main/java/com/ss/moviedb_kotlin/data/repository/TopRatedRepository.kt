package com.ss.moviedb_kotlin.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ss.moviedb_kotlin.data.paging.TopRatedRemoteMediator
import com.ss.moviedb_kotlin.db.remote.MovieDatabase
import com.ss.moviedb_kotlin.model.movies.TopRatedMovie
import com.ss.moviedb_kotlin.network.MovieDbApi
import com.ss.moviedb_kotlin.util.Const
import kotlinx.coroutines.flow.Flow

class TopRatedRepository(
    private val movieDbApi: MovieDbApi,
    private val database: MovieDatabase
) {
    fun getTopRatedMoviesStream(): Flow<PagingData<TopRatedMovie>> {
        // * Directly from network
//        val pagingSourceFactory = { TopRatedPagingSource(movieDbApi) }
        // * Caching from Room
        val pagingSourceFactory = { database.movieDatabaseDao().getTopRatedMovies() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = Const.MOVIEDB_PAGE_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = TopRatedRemoteMediator(
                movieDbApi,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}