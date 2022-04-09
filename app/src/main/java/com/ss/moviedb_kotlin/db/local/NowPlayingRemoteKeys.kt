package com.ss.moviedb_kotlin.db.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ss.moviedb_kotlin.util.Const

@Entity(tableName = Const.NOW_PLAYING_REMOTE_KEYS_TABLE)
data class NowPlayingRemoteKeys(
    @PrimaryKey
    val id: Long,
    val prevPage: Int?,
    val nextPage: Int?,
)
