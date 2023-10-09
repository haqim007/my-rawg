package dev.haqim.myrawg.domain.usecase

import dagger.hilt.android.scopes.ViewModelScoped
import dev.haqim.myrawg.domain.repository.IGameCollectionsRepository
import javax.inject.Inject

@ViewModelScoped
class RemoveCollectionUseCase @Inject constructor(
    private val gameCollectionsRepository: IGameCollectionsRepository
) {
    suspend operator fun invoke(id: Int) = gameCollectionsRepository.removeCollection(id)
}