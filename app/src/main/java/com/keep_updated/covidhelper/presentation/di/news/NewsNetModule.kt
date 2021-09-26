package com.keep_updated.covidhelper.presentation.di

import com.keep_updated.covidhelper.BuildConfig
import com.keep_updated.covidhelper.data.api.NewsAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NewsNetModule {


    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }


    @Singleton
    @Provides
    fun provideBlogService(retrofit: Retrofit.Builder): NewsAPIService {
        return retrofit
            .build()
            .create(NewsAPIService::class.java)
    }
}