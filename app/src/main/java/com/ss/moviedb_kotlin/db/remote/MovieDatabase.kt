package com.ss.moviedb_kotlin.db.remote

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ss.moviedb_kotlin.db.local.NowPlayingRemoteKeys
import com.ss.moviedb_kotlin.model.movies.NowPlayingMovie
import com.ss.moviedb_kotlin.db.local.NowPlayingRemoteKeysDao
import com.ss.moviedb_kotlin.db.local.UpcomingRemoteKeys
import com.ss.moviedb_kotlin.db.local.UpcomingRemoteKeysDao
import com.ss.moviedb_kotlin.model.movies.UpcomingMovie
import com.ss.moviedb_kotlin.util.Const

@Database(
    entities = [
        NowPlayingMovie::class, NowPlayingRemoteKeys::class,
        UpcomingMovie::class, UpcomingRemoteKeys::class],
    version = 2,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    // * DAO
    abstract fun movieDatabaseDao(): MovieDatabaseDao
    abstract fun nowPlayingRemoteKeysDao(): NowPlayingRemoteKeysDao
    abstract fun upcomingRemoteKeysDao(): UpcomingRemoteKeysDao

    // * Singleton instance
    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }


        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MovieDatabase::class.java,
                Const.MOVIEDB_DATABASE
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}