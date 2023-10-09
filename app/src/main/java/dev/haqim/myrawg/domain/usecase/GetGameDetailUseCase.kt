package dev.haqim.myrawg.domain.usecase

import dagger.hilt.android.scopes.ViewModelScoped
import dev.haqim.myrawg.domain.repository.IGameDetailRepository
import javax.inject.Inject

@ViewModelScoped
class GetGameDetailUseCase @Inject constructor(
    private val gameDetailRepository: IGameDetailRepository
) {
    operator fun invoke(id: Int) = gameDetailRepository.getById(id)
}