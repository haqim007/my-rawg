package dev.haqim.myrawg.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.RemoteMediator
import androidx.paging.map
import dev.haqim.myrawg.data.local.LocalDataSource
import dev.haqim.myrawg.data.pagingsource.GamesRemoteMediator
import dev.haqim.myrawg.data.remote.RemoteDataSource
import dev.haqim.myrawg.data.util.DEFAULT_PAGE_SIZE
import dev.haqim.myrawg.di.DispatcherIO
import dev.haqim.myrawg.domain.model.GamesListItem
import dev.haqim.myrawg.domain.repository.IGamesListRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GamesListRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    @DispatcherIO private val dispatcher: CoroutineDispatcher
): IGamesListRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getAll(search: String?): Flow<PagingData<GamesListItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = false,
                maxSize = 3 * DEFAULT_PAGE_SIZE
            ),
            remoteMediator = GamesRemoteMediator(
                getRemoteKeyById = {
                    localDataSource.getRemoteKeyById(it)
                },
                insertKeysAndGameListItems = { keys, gameListItems, isRefresh ->
                    localDataSource.insertKeysAndGameListItems(keys, gameListItems, isRefresh)
                },
                fetchGamesList = { page, pageSize ->  
                    remoteDataSource.getGamesList(page, pageSize, search)
                }
            ),
            pagingSourceFactory = {
                if(!search.isNullOrEmpty()){
                    val dbQuery = "%${search.replace(' ', '%')}%"
                    localDataSource.searchGameListItems(dbQuery)
                }else{
                    localDataSource.getAllGameListItems()
                }
                
            }
        ).flow.flowOn(dispatcher).map { pagingData ->
            pagingData.map { 
                it.toModel()
            }
        }
    }
}