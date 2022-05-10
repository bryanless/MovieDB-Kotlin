package com.ss.moviedb_kotlin.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ss.moviedb_kotlin.db.local.RecommendationRemoteKeys
import com.ss.moviedb_kotlin.db.remote.MovieDatabase
import com.ss.moviedb_kotlin.model.movies.RecommendationMovie
import com.ss.moviedb_kotlin.network.MovieDbApi
import com.ss.moviedb_kotlin.util.Const
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class RecommendationRemoteMediator(
    private val movieDbApi: MovieDbApi,
    private val movieDatabase: MovieDatabase,
    private val movieId: Int
) : RemoteMediator<Int, RecommendationMovie>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RecommendationMovie>
    ): RemoteMediator.MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: Const.MOVIEDB_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                val prevKey = remoteKeys?.prevKey
                    ?: return RemoteMediator.MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with endOfPaginationReached = false because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                    ?: return RemoteMediator.MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val apiResponse =
                movieDbApi.retrofitService.getRecommendation(movieId, page, Const.API_KEY)

            val movies = apiResponse.results
            val endOfPaginationReached = movies.isEmpty()
            movieDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    movieDatabase.recommendationRemoteKeysDao().clearRecommendationRemoteKeys()
                    movieDatabase.movieDatabaseDao().clearRecommendationMovies()
                }
                val prevKey = if (page == Const.MOVIEDB_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = movies.map {
                    RecommendationRemoteKeys(
                        id = it.id.toLong(),
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                movieDatabase.recommendationRemoteKeysDao().insertRecommendationRemoteKeys(keys)
                movieDatabase.movieDatabaseDao().insertRecommendationMovies(movies)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, RecommendationMovie>): RecommendationRemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                // Get the remote keys of the last item retrieved
                movieDatabase.recommendationRemoteKeysDao()
                    .getRecommendationRemoteKey(movie.id.toLong())
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, RecommendationMovie>): RecommendationRemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                // Get the remote keys of the first items retrieved
                movieDatabase.recommendationRemoteKeysDao()
                    .getRecommendationRemoteKey(movie.id.toLong())
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, RecommendationMovie>
    ): RecommendationRemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { movieId ->
                movieDatabase.recommendationRemoteKeysDao()
                    .getRecommendationRemoteKey(movieId.toLong())
            }
        }
    }
}