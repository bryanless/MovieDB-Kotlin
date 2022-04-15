package com.ss.moviedb_kotlin.network.response

import com.ss.moviedb_kotlin.model.movies.UpcomingMovie

data class UpcomingResponse(
    val page: Int = 0,
    val results: List<UpcomingMovie> = listOf(),
    val total_pages: Int = 0,
    val total_results: Int = 0
)
