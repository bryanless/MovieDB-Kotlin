package com.ss.moviedb_kotlin.db.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ss.moviedb_kotlin.util.Const

@Entity(tableName = Const.RECOMMENDATION_REMOTE_KEYS_TABLE)
data class RecommendationRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val prevKey: Int?,
    val nextKey: Int?,
)