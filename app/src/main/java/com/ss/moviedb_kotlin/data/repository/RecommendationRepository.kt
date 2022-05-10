package com.ss.moviedb_kotlin.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ss.moviedb_kotlin.data.paging.RecommendationRemoteMediator
import com.ss.moviedb_kotlin.db.remote.MovieDatabase
import com.ss.moviedb_kotlin.model.movies.RecommendationMovie
import com.ss.moviedb_kotlin.network.MovieDbApi
import com.ss.moviedb_kotlin.util.Const
import kotlinx.coroutines.flow.Flow

class RecommendationRepository(
    private val movieDbApi: MovieDbApi,
    private val database: MovieDatabase,
    private val movieId: Int
) {
    fun getRecommendationMoviesStream(): Flow<PagingData<RecommendationMovie>> {
        // * Directly from network
//        val pagingSourceFactory = { RecommendationPagingSource(movieDbApi) }
        // * Caching from Room
        val pagingSourceFactory = { database.movieDatabaseDao().getRecommendationMovies() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = Const.MOVIEDB_PAGE_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = RecommendationRemoteMediator(
                movieDbApi,
                database,
                movieId
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}