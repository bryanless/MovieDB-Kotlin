package com.ss.moviedb_kotlin.model.movies

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.squareup.moshi.Json
import com.ss.moviedb_kotlin.util.Const
import com.ss.moviedb_kotlin.util.DataConverter

@Entity(tableName = Const.NOW_PLAYING_MOVIES_TABLE)
@TypeConverters(DataConverter::class)
data class NowPlayingMovie(
    @PrimaryKey(autoGenerate = true)
    var remoteId: Long = 0,
    @Json(name = "adult") val adult: Boolean?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "genre_ids") val genreIds: List<Int>?,
    @Json(name = "id") var id: Int,
    @Json(name = "original_language") val originalLanguage: String?,
    @Json(name = "original_title") val originalTitle: String?,
    @Json(name = "overview") val overview: String?,
    @Json(name = "popularity") val popularity: Double?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "release_date") val releaseDate: String?,
    @Json(name = "title") val title: String?,
    @Json(name = "video") val video: Boolean?,
    @Json(name = "vote_average") val voteAverage: Double?,
    @Json(name = "vote_count") val voteCount: Int?
)