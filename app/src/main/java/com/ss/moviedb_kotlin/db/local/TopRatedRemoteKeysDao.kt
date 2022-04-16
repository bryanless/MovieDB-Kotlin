package com.ss.moviedb_kotlin.db.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TopRatedRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopRatedRemoteKeys(remoteKeys: List<TopRatedRemoteKeys>)

    @Query("SELECT * FROM top_rated_remote_keys WHERE id = :id")
    suspend fun getTopRatedRemoteKey(id: Long): TopRatedRemoteKeys?

    @Query("DELETE FROM top_rated_remote_keys")
    suspend fun clearTopRatedRemoteKeys()
}