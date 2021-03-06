package com.ss.moviedb_kotlin.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ss.moviedb_kotlin.data.paging.RecommendationRemoteMediator
import com.ss.moviedb_kotlin.db.remote.MovieDatabase
import com.ss.moviedb_kotlin.model.movies.DetailMovie
import com.ss.moviedb_kotlin.model.movies.RecommendationMovie
import com.ss.moviedb_kotlin.model.movies.VideoMovie
import com.ss.moviedb_kotlin.network.MovieDbApi
import com.ss.moviedb_kotlin.network.response.VideoResponse
import com.ss.moviedb_kotlin.util.Const
import kotlinx.coroutines.flow.Flow

class DetailRepository(
    private val movieDbApi: MovieDbApi,
    private val database: MovieDatabase,
    private val movieId: Int
) {
    suspend fun getDetailMovie(movieId: Int): DetailMovie {
        return movieDbApi.retrofitService.getMovieById(movieId, Const.API_KEY)
    }

    suspend fun getVideosMovie(movieId: Int): List<VideoMovie> {
        return movieDbApi.retrofitService.getVideo(movieId, Const.API_KEY).results
    }

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