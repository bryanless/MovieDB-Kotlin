package com.ss.moviedb_kotlin.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.ss.moviedb_kotlin.db.remote.MovieDatabase
import com.ss.moviedb_kotlin.model.movies.NowPlayingMovie
import com.ss.moviedb_kotlin.network.MovieDbApi

@ExperimentalPagingApi
class NowPlayingRemoteMediator(
    private val movieDbApi: MovieDbApi,
    private val movieDatabase: MovieDatabase
) : RemoteMediator<Int, NowPlayingMovie>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NowPlayingMovie>
    ): MediatorResult {
        return MediatorResult.Success(true)
    }
}