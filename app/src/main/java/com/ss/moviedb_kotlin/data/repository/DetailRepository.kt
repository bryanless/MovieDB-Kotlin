package com.ss.moviedb_kotlin.data.repository

import com.ss.moviedb_kotlin.model.movies.DetailMovie
import com.ss.moviedb_kotlin.network.MovieDbApi
import com.ss.moviedb_kotlin.util.Const

class DetailRepository(private val movieDbApi: MovieDbApi) {
    suspend fun getDetailMovie(movieId: Int): DetailMovie {
        return movieDbApi.retrofitService.getMovieById(movieId, Const.API_KEY)
    }
}