package com.ss.moviedb_kotlin.db.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TrendingRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrendingRemoteKeys(remoteKeys: List<TrendingRemoteKeys>)

    @Query("SELECT * FROM trending_remote_keys WHERE id = :id")
    suspend fun getTrendingRemoteKey(id: Long): TrendingRemoteKeys?

    @Query("DELETE FROM trending_remote_keys")
    suspend fun clearTrendingRemoteKeys()
}