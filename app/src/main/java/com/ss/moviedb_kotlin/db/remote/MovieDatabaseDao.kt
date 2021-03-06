package com.ss.moviedb_kotlin.db.remote

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.ss.moviedb_kotlin.model.movies.*

@Dao
interface MovieDatabaseDao {
    //==Start of Trending
    @Insert(onConflict = REPLACE)
    suspend fun insertTrendingMovies(trendingMovieList: List<TrendingMovie>)

    @Query("SELECT * FROM trending_movies ORDER BY remoteId ASC")
    fun getTrendingMovies(): PagingSource<Int, TrendingMovie>

    @Query("DELETE FROM trending_movies")
    suspend fun clearTrendingMovies()
    //==End of Trending

    //==Start of Popular
    @Insert(onConflict = REPLACE)
    suspend fun insertPopularMovies(popularMovieList: List<PopularMovie>)

    @Query("SELECT * FROM popular_movies ORDER BY remoteId ASC")
    fun getPopularMovies(): PagingSource<Int, PopularMovie>

    @Query("DELETE FROM popular_movies")
    suspend fun clearPopularMovies()
    //==End of Popular

    //==Start of Top Rated
    @Insert(onConflict = REPLACE)
    suspend fun insertTopRatedMovies(topRatedMovieList: List<TopRatedMovie>)

    @Query("SELECT * FROM top_rated_movies ORDER BY remoteId ASC")
    fun getTopRatedMovies(): PagingSource<Int, TopRatedMovie>

    @Query("DELETE FROM top_rated_movies")
    suspend fun clearTopRatedMovies()
    //==End of Top Rated

    //==Start of Now Playing
    @Insert(onConflict = REPLACE)
    suspend fun insertNowPlayingMovies(nowPlayingMovieList: List<NowPlayingMovie>)

    @Query("SELECT * FROM now_playing_movies ORDER BY remoteId ASC")
    fun getNowPlayingMovies(): PagingSource<Int, NowPlayingMovie>

    @Query("DELETE FROM now_playing_movies")
    suspend fun clearNowPlayingMovies()
    //==End of Now Playing

    //==Start of Upcoming
    @Insert(onConflict = REPLACE)
    suspend fun insertUpcomingMovies(upcomingMovieList: List<UpcomingMovie>)

    @Query("SELECT * FROM upcoming_movies ORDER BY remoteId ASC")
    fun getUpcomingMovies(): PagingSource<Int, UpcomingMovie>

    @Query("DELETE FROM upcoming_movies")
    suspend fun clearUpcomingMovies()
    //==End of Upcoming

    //==Start of Recommendation
    @Insert(onConflict = REPLACE)
    suspend fun insertRecommendationMovies(recommendationMovieList: List<RecommendationMovie>)

    @Query("SELECT * FROM recommendation_movies ORDER BY remoteId ASC")
    fun getRecommendationMovies(): PagingSource<Int, RecommendationMovie>

    @Query("DELETE FROM recommendation_movies")
    suspend fun clearRecommendationMovies()
    //==End of Recommendation

}