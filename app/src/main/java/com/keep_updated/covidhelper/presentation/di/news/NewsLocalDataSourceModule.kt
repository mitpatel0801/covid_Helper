package com.keep_updated.covidhelper.presentation.di.news

import com.keep_updated.covidhelper.data.db.ArticleDao
import com.keep_updated.covidhelper.data.repository.dataSource.NewsLocalDataSource
import com.keep_updated.covidhelper.data.repository.dataSourceImpl.NewsLocalDataSourceImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsLocalDataSourceModule {

    @Singleton
    @Provides
    fun provideLocalNewsDataSource(articleDao: ArticleDao): NewsLocalDataSource {
        return NewsLocalDataSourceImp(articleDao)
    }
}