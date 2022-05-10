package com.ss.moviedb_kotlin.db.remote

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ss.moviedb_kotlin.db.local.*
import com.ss.moviedb_kotlin.model.movies.*
import com.ss.moviedb_kotlin.util.Const

@Database(
    entities = [
        TrendingMovie::class, TrendingRemoteKeys::class,
        PopularMovie::class, PopularRemoteKeys::class,
        TopRatedMovie::class, TopRatedRemoteKeys::class,
        NowPlayingMovie::class, NowPlayingRemoteKeys::class,
        UpcomingMovie::class, UpcomingRemoteKeys::class,
        RecommendationMovie::class, RecommendationRemoteKeys::class],
    version = 6,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    // * DAO
    abstract fun movieDatabaseDao(): MovieDatabaseDao
    abstract fun trendingRemoteKeysDao(): TrendingRemoteKeysDao
    abstract fun popularRemoteKeysDao(): PopularRemoteKeysDao
    abstract fun topRatedRemoteKeysDao(): TopRatedRemoteKeysDao
    abstract fun nowPlayingRemoteKeysDao(): NowPlayingRemoteKeysDao
    abstract fun upcomingRemoteKeysDao(): UpcomingRemoteKeysDao
    abstract fun recommendationRemoteKeysDao(): RecommendationRemoteKeysDao

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