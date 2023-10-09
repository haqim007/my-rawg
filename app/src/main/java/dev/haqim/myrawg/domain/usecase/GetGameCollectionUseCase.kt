package dev.haqim.myrawg.domain.usecase

import androidx.paging.PagingData
import dagger.hilt.android.scopes.ViewModelScoped
import dev.haqim.myrawg.domain.model.GamesListItem
import dev.haqim.myrawg.domain.repository.IGameCollectionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class GetGameCollectionUseCase @Inject constructor(
    private val gameCollectionsRepository: IGameCollectionsRepository
) {
    operator fun invoke(search: String?): Flow<PagingData<GamesListItem>> {
        val query = if (search?.isEmpty() == true && search.isBlank()){
            null
        }else{
            search
        }
        return gameCollectionsRepository.getAll(query)
    }
}