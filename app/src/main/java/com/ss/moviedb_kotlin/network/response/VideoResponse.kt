package com.ss.moviedb_kotlin.network.response

import com.ss.moviedb_kotlin.model.movies.VideoMovie

data class VideoResponse(
    val id: Int = 0,
    val results: List<VideoMovie> = listOf(),
)