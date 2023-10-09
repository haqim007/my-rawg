package dev.haqim.myrawg.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.haqim.myrawg.data.remote.base.ApiConfig
import dev.haqim.myrawg.data.remote.base.GamesService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
 
    
    @Singleton
    @Provides
    fun provideGameService(apiConfig: ApiConfig) = apiConfig.createService(GamesService::class.java)
}