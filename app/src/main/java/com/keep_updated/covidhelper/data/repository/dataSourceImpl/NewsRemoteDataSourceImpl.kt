package com.keep_updated.covidhelper.data.repository.dataSourceImpl

import com.keep_updated.covidhelper.data.api.NewsAPIService
import com.keep_updated.covidhelper.data.models.NewsAPIResponse
import com.keep_updated.covidhelper.data.repository.dataSource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(private val newsAPIService: NewsAPIService) : NewsRemoteDataSource {
    override suspend fun getTopHeadLine(page: Int): Response<NewsAPIResponse> {
        return newsAPIService.getTopHeadlines(page)
    }
}