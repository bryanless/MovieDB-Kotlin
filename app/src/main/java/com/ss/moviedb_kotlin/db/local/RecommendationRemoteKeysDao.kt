package com.ss.moviedb_kotlin.db.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecommendationRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecommendationRemoteKeys(remoteKeys: List<RecommendationRemoteKeys>)

    @Query("SELECT * FROM recommendation_remote_keys WHERE id = :id")
    suspend fun getRecommendationRemoteKey(id: Long): RecommendationRemoteKeys?

    @Query("DELETE FROM recommendation_remote_keys")
    suspend fun clearRecommendationRemoteKeys()
}