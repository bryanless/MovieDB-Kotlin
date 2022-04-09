package com.ss.moviedb_kotlin.network.response

import com.ss.moviedb_kotlin.model.movies.NowPlayingMovie

data class NowPlayingResponse(
    val page: Int = 0,
    val results: List<NowPlayingMovie> = listOf(),
    val total_pages: Int = 0,
    val total_results: Int = 0
)