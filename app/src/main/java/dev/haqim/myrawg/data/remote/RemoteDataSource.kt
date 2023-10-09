package dev.haqim.myrawg.data.remote

import dev.haqim.myrawg.data.mechanism.getResult
import dev.haqim.myrawg.data.remote.base.GamesService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val service: GamesService
) {
    suspend fun getGamesList(page: Int, pageSize: Int, search: String? = null) = 
        getResult { 
            service.getGames(page = page, pageSize = pageSize, search = search)
        }
    
    suspend fun getGame(id: Int) = getResult { 
        service.getGame(id)
    }
}