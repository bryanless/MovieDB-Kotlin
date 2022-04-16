package com.ss.moviedb_kotlin.db.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PopularRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularRemoteKeys(remoteKeys: List<PopularRemoteKeys>)

    @Query("SELECT * FROM popular_remote_keys WHERE id = :id")
    suspend fun getPopularRemoteKey(id: Long): PopularRemoteKeys?

    @Query("DELETE FROM popular_remote_keys")
    suspend fun clearPopularRemoteKeys()
}