package dev.haqim.myrawg.domain.usecase

import androidx.paging.PagingData
import dagger.hilt.android.scopes.ViewModelScoped
import dev.haqim.myrawg.domain.model.GamesListItem
import dev.haqim.myrawg.domain.repository.IGamesListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class GetGameListUseCase @Inject constructor(
    private val gamesListRepository: IGamesListRepository
) {
    operator fun invoke(search: String? = null): Flow<PagingData<GamesListItem>> {
        val query = if (search?.isEmpty() == true && search.isBlank()){
            null
        }else{
            search
        }    
        return gamesListRepository.getAll(query) 
    }
}