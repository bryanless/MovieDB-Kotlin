package com.ss.moviedb_kotlin.db.remote

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.ss.moviedb_kotlin.model.movies.NowPlayingMovie

@Dao
interface MovieDatabaseDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertNowPlayingMovies(nowPlayingMovieList: List<NowPlayingMovie>)

    @Query("SELECT * FROM now_playing_movies ORDER BY remoteId ASC")
    fun getNowPlayingMovies(): PagingSource<Int, NowPlayingMovie>

    @Query("DELETE FROM now_playing_movies")
    suspend fun clearNowPlayingMovies()
}