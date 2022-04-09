package com.ss.moviedb_kotlin.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.ss.moviedb_kotlin.util.Const
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Const.BASE_URL)
    .build()

object MovieDbApi {
    val retrofitService: MovieDbApiEndPoint by lazy {
        retrofit.create(MovieDbApiEndPoint::class.java)
    }
}
