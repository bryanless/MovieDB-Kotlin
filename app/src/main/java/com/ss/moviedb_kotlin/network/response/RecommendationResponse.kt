package com.ss.moviedb_kotlin.network.response

import com.ss.moviedb_kotlin.model.movies.RecommendationMovie

data class RecommendationResponse(
    val page: Int = 0,
    val results: List<RecommendationMovie> = listOf(),
    val total_pages: Int = 0,
    val total_results: Int = 0
)
