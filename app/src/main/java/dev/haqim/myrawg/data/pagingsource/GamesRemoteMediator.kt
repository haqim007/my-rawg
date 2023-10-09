package dev.haqim.myrawg.data.pagingsource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import dev.haqim.myrawg.data.local.entity.GamesListItemEntity
import dev.haqim.myrawg.data.local.entity.RemoteKeys
import dev.haqim.myrawg.data.remote.response.GamesResponse
import dev.haqim.myrawg.data.remote.response.toEntity
import javax.inject.Inject


@OptIn(ExperimentalPagingApi::class)
class GamesRemoteMediator @Inject constructor(
    private val getRemoteKeyById: suspend (id: String) -> RemoteKeys?,
    private val insertKeysAndGameListItems: suspend (keys: List<RemoteKeys>, gameListItems: List<GamesListItemEntity>, isRefresh: Boolean) -> Unit,
    private val fetchGamesList: suspend (
        page: Int,
        pageSize: Int
    ) -> Result<GamesResponse>,
): RemoteMediator<Int, GamesListItemEntity>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, GamesListItemEntity>): MediatorResult {
        
        val page = when(loadType){
            LoadType.REFRESH -> {
                val remoteKeys: RemoteKeys? = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys: RemoteKeys? = getRemoteKeyForFirstItem(state)
                val prevKey: Int = remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys: RemoteKeys? = getRemoteKeyForLastItem(state)
                val nextKey: Int = remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        val result = try {
            val response = fetchGamesList(page, state.config.pageSize)
            val endOfPaginationReached = response.getOrNull()?.nextUrl == null

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (endOfPaginationReached) null else page + 1
            val gamesList = response.getOrNull()?.results ?: listOf()
            val keys = gamesList.map {
                RemoteKeys(
                    it.id.toString(),
                    prevKey,
                    nextKey
                )
            }

            insertKeysAndGameListItems(
                keys,
                gamesList.toEntity(),
                loadType == LoadType.REFRESH
            )
            
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }catch (e: Exception){
            MediatorResult.Error(e)
        }

        return result

    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, GamesListItemEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                getRemoteKeyById(id.toString())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, GamesListItemEntity>): RemoteKeys?{
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { data ->
                getRemoteKeyById(data.id.toString())
            }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, GamesListItemEntity>): RemoteKeys?{
        return state.pages.lastOrNull{it.data.isNotEmpty()}?.data?.lastOrNull()?.let { data ->
            getRemoteKeyById(data.id.toString())
        }
    }

     companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}