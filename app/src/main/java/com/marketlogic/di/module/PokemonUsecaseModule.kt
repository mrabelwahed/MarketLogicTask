package com.marketlogic.di.module

import com.marketlogic.domain.NewsUsecase
import com.marketlogic.repository.NewsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PokemonUsecaseModule {
    @Provides
    @Singleton
    fun provideFeedUseCase(repository :NewsRepository) = NewsUsecase(repository)
}