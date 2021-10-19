package com.keep_updated.covidhelper.presentation.di

import com.keep_updated.covidhelper.data.api.NewsAPIService
import com.keep_updated.covidhelper.data.repository.dataSource.NewsRemoteDataSource
import com.keep_updated.covidhelper.data.repository.dataSourceImpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NewsRemoteDataSourceModule {

    @Singleton
    @Provides
    fun provideRemoteNewsDataSource(newsAPIService: NewsAPIService): NewsRemoteDataSource {
        return NewsRemoteDataSourceImpl(newsAPIService)
    }
}