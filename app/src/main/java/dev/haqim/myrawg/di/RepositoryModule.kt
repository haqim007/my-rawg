package dev.haqim.myrawg.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import dev.haqim.myrawg.data.repository.GameCollectionsRepository
import dev.haqim.myrawg.data.repository.GameDetailRepository
import dev.haqim.myrawg.data.repository.GamesListRepository
import dev.haqim.myrawg.domain.repository.IGameCollectionsRepository
import dev.haqim.myrawg.domain.repository.IGameDetailRepository
import dev.haqim.myrawg.domain.repository.IGamesListRepository

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    @Binds
    @ViewModelScoped
    fun provideGameListRepository(gamesListRepository: GamesListRepository): IGamesListRepository

    @Binds
    @ViewModelScoped
    fun provideGameDetailRepository(gameDetailRepository: GameDetailRepository): IGameDetailRepository

    @Binds
    @ViewModelScoped
    fun provideGameCollectionsRepository(gameCollectionsRepository: GameCollectionsRepository): IGameCollectionsRepository
}