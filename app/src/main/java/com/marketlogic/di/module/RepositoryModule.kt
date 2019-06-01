package com.marketlogic.di.module

import com.marketlogic.network.NewsApi
import com.marketlogic.repository.NewsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideFeedRepository(api: NewsApi) = NewsRepository(api)
}