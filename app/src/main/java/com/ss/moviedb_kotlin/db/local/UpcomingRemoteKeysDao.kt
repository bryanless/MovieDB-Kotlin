package com.ss.moviedb_kotlin.db.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UpcomingRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpcomingRemoteKeys(remoteKeys: List<UpcomingRemoteKeys>)

    @Query("SELECT * FROM upcoming_remote_keys WHERE id = :id")
    suspend fun getUpcomingRemoteKey(id: Long): UpcomingRemoteKeys?

    @Query("DELETE FROM upcoming_remote_keys")
    suspend fun clearUpcomingRemoteKeys()
}