package dev.haqim.myrawg.data.repository

import dev.haqim.myrawg.data.local.LocalDataSource
import dev.haqim.myrawg.data.mechanism.NetworkBoundResource
import dev.haqim.myrawg.data.mechanism.Resource
import dev.haqim.myrawg.data.remote.RemoteDataSource
import dev.haqim.myrawg.data.remote.response.GameDetailResponse
import dev.haqim.myrawg.di.DispatcherIO
import dev.haqim.myrawg.domain.model.GameDetail
import dev.haqim.myrawg.domain.repository.IGameDetailRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GameDetailRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    @DispatcherIO private val dispatcher: CoroutineDispatcher
): IGameDetailRepository {
    override fun getById(id: Int): Flow<Resource<GameDetail?>> {
        return object: NetworkBoundResource<GameDetail?, GameDetailResponse>(){
            override suspend fun requestFromRemote(): Result<GameDetailResponse> {
                return remoteDataSource.getGame(id)
            }

            override fun loadResult(data: GameDetailResponse): Flow<GameDetail?> {
               return localDataSource.getGameDetail(id).map { 
                   it?.toModel()
               }
            }

            override suspend fun onFetchSuccess(data: GameDetailResponse) {
                localDataSource.insertGameDetail(data.toEntity())
            }
            
            
        }.asFlow().flowOn(dispatcher)
    }
}