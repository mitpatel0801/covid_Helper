package com.keep_updated.covidhelper.presentation.di.news

import com.keep_updated.covidhelper.data.repository.NewsRepositoryImpl
import com.keep_updated.covidhelper.data.repository.dataSource.NewsLocalDataSource
import com.keep_updated.covidhelper.data.repository.dataSource.NewsRemoteDataSource
import com.keep_updated.covidhelper.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NewsRepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepository(
        newsRemoteDataSource: NewsRemoteDataSource,
        newsLocalDataSource: NewsLocalDataSource
    ): NewsRepository {
        return NewsRepositoryImpl(newsRemoteDataSource, newsLocalDataSource)
    }

}