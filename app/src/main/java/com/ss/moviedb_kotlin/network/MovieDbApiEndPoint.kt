package com.ss.moviedb_kotlin.network

import com.ss.moviedb_kotlin.network.response.NowPlayingResponse
import com.ss.moviedb_kotlin.network.response.UpcomingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDbApiEndPoint {
    //==Start of movie API
    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): NowPlayingResponse

        @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): UpcomingResponse
    //==End of movie API
}