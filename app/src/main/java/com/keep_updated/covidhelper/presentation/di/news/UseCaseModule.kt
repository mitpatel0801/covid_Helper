package com.keep_updated.covidhelper.presentation.di.news

import com.keep_updated.covidhelper.domain.repository.NewsRepository
import com.keep_updated.covidhelper.domain.usecase.GetNewsHeadlineUseCase
import com.keep_updated.covidhelper.domain.usecase.GetSearchedNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideNewsDataSource(newsRepository: NewsRepository): GetNewsHeadlineUseCase {
        return GetNewsHeadlineUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideSearchedNews(newsRepository: NewsRepository): GetSearchedNewsUseCase {
        return GetSearchedNewsUseCase(newsRepository)
    }
}