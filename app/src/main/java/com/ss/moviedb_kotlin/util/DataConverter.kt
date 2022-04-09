package com.ss.moviedb_kotlin.util

import androidx.room.TypeConverter
import com.google.gson.Gson

class DataConverter {
    @TypeConverter
    fun fromList(value: List<Int>?) = Gson().toJson(value)

    @TypeConverter
    fun fromString(value: String) = Gson().fromJson(value, Array<Int>::class.java).toList()
}