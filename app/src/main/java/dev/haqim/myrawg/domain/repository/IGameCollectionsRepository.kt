package dev.haqim.myrawg.domain.repository

import androidx.paging.PagingData
import dev.haqim.myrawg.data.mechanism.Resource
import dev.haqim.myrawg.domain.model.BasicMessage
import dev.haqim.myrawg.domain.model.GameDetail
import dev.haqim.myrawg.domain.model.GamesListItem
import kotlinx.coroutines.flow.Flow

interface IGameCollectionsRepository {
    fun getAll(search: String? = null): Flow<PagingData<GamesListItem>>
    suspend fun addCollection(game: GameDetail): Resource<BasicMessage>
    suspend fun removeCollection(id: Int): Resource<BasicMessage>
}