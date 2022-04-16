package com.ss.moviedb_kotlin.network

import com.ss.moviedb_kotlin.network.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDbApiEndPoint {
    //==Start of movie API
//    @GET("movie/{movie_id}")
//    suspend fun getMovieById(
//        @Path("movie_id") movieId: String,
//        @Query("api_key") apiKey: String
//    ): MovieDetail

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

    @GET("movie/popular")
    suspend fun getPopular(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): PopularResponse

    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): TopRatedResponse

//    @GET("movie/{movie_id}/videos")
//    fun getVideo(
//        @Path("movie_id") movieId: String?,
//        @Query("api_key") apiKey: String?
//    ): Call<Videos?>?
    //==End of movie API

    //==End of genre API
    //==Start of trending API
    @GET("trending/{media_type}/{time_window}")
    suspend fun getTrending(
        @Path("media_type") mediaType: String,
        @Path("time_window") timeWindow: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): TrendingResponse
    //==End of trending API
}