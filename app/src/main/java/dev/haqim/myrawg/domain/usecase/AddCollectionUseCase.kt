package dev.haqim.myrawg.domain.usecase

import dagger.hilt.android.scopes.ViewModelScoped
import dev.haqim.myrawg.domain.model.GameDetail
import dev.haqim.myrawg.domain.repository.IGameCollectionsRepository
import javax.inject.Inject

@ViewModelScoped
class AddCollectionUseCase @Inject constructor(
    private val gameCollectionsRepository: IGameCollectionsRepository
) {
    suspend operator fun invoke(game: GameDetail) = gameCollectionsRepository.addCollection(game)
}