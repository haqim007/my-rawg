package dev.haqim.myrawg.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.RemoteMediator
import androidx.paging.map
import dev.haqim.myrawg.data.local.LocalDataSource
import dev.haqim.myrawg.data.local.entity.toGameCollectionEntity
import dev.haqim.myrawg.data.mechanism.Resource
import dev.haqim.myrawg.data.pagingsource.GameCollectionsPagingSource
import dev.haqim.myrawg.data.pagingsource.GamesRemoteMediator
import dev.haqim.myrawg.data.remote.RemoteDataSource
import dev.haqim.myrawg.data.util.DEFAULT_PAGE_SIZE
import dev.haqim.myrawg.di.DispatcherIO
import dev.haqim.myrawg.domain.model.BasicMessage
import dev.haqim.myrawg.domain.model.GameDetail
import dev.haqim.myrawg.domain.model.GamesListItem
import dev.haqim.myrawg.domain.repository.IGameCollectionsRepository
import dev.haqim.myrawg.domain.repository.IGamesListRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GameCollectionsRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    @DispatcherIO private val dispatcher: CoroutineDispatcher
): IGameCollectionsRepository {
    override fun getAll(search: String?): Flow<PagingData<GamesListItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE
            ),
            pagingSourceFactory = {
                GameCollectionsPagingSource{ limit, offset ->
                    
                    if(!search.isNullOrEmpty()){
                        val dbQuery = "%${search.replace(' ', '%')}%"
                        localDataSource.searchCollections(dbQuery, limit, offset)
                    }else{
                        localDataSource.getAllCollections(limit, offset)
                    }
                }
            }
        ).flow.flowOn(dispatcher)
    }

    override suspend fun addCollection(game: GameDetail): Resource<BasicMessage> {
        return try {
            localDataSource.addCollection(game.toGameCollectionEntity())
            Resource.Success(BasicMessage("Success"))
        }catch (e: Exception){
            Resource.Error(message = e.localizedMessage ?: "Unknown Error")
        }
    }

    override suspend fun removeCollection(id: Int): Resource<BasicMessage> {
        return try {
            localDataSource.removeCollection(id)
            Resource.Success(BasicMessage("Success"))
        }catch (e: Exception){
            Resource.Error(message = e.localizedMessage ?: "Unknown Error")
        }
    }
}