package dev.haqim.myrawg.domain.repository

import androidx.paging.PagingData
import dev.haqim.myrawg.data.mechanism.Resource
import dev.haqim.myrawg.domain.model.BasicMessage
import dev.haqim.myrawg.domain.model.GameDetail
import dev.haqim.myrawg.domain.model.GamesListItem
import kotlinx.coroutines.flow.Flow

interface IGameDetailRepository {
    fun getById(id: Int): Flow<Resource<GameDetail?>>
}