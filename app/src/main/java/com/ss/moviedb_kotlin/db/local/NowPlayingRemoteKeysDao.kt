package com.ss.moviedb_kotlin.db.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface NowPlayingRemoteKeysDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertNowPlayingRemoteKeys(remoteKeys: List<NowPlayingRemoteKeys>)

    @Query("SELECT * FROM now_playing_remote_keys WHERE id = :id")
    suspend fun getNowPlayingRemoteKey(id: Long): NowPlayingRemoteKeys?

    @Query("DELETE FROM now_playing_remote_keys")
    suspend fun clearNowPlayingRemoteKeys()
}