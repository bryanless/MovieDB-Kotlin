package com.ss.moviedb_kotlin.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ss.moviedb_kotlin.model.movies.NowPlayingMovie
import com.ss.moviedb_kotlin.network.MovieDbApi
import com.ss.moviedb_kotlin.network.MovieDbApiEndPoint
import com.ss.moviedb_kotlin.util.Const
import retrofit2.HttpException
import java.io.IOException

class NowPlayingPagingSource(
    private val movieDbApi: MovieDbApi
) : PagingSource<Int, NowPlayingMovie>() {
    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<Int, NowPlayingMovie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NowPlayingMovie> {
        val position = params.key ?: Const.MOVIEDB_STARTING_PAGE_INDEX

        return try {
            val response = movieDbApi.retrofitService.getNowPlaying(position, Const.API_KEY)
            val nowPlayingMovies = response.results
            val nextKey = if (nowPlayingMovies.isEmpty()) {
                null
            } else {
                position + (params.loadSize / Const.MOVIEDB_PAGE_SIZE)
            }

            LoadResult.Page(
                data = nowPlayingMovies,
                prevKey = if (position == Const.MOVIEDB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

}