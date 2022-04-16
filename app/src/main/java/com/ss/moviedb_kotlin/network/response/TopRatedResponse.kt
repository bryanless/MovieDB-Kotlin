package com.ss.moviedb_kotlin.network.response

import com.ss.moviedb_kotlin.model.movies.TopRatedMovie

data class TopRatedResponse(
    val page: Int = 0,
    val results: List<TopRatedMovie> = listOf(),
    val total_pages: Int = 0,
    val total_results: Int = 0
)