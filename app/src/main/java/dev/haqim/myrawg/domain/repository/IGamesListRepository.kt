package dev.haqim.myrawg.domain.repository

import androidx.paging.PagingData
import dev.haqim.myrawg.domain.model.GamesListItem
import kotlinx.coroutines.flow.Flow

interface IGamesListRepository {
    fun getAll(search: String? = null): Flow<PagingData<GamesListItem>>
    
}