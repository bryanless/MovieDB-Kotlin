package com.ss.moviedb_kotlin.model.movies

import com.squareup.moshi.Json

data class GenreMovie(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String?
)