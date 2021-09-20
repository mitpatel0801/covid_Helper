package com.keep_updated.covidhelper.data.repository.dataSource

import com.keep_updated.covidhelper.data.models.NewsAPIResponse
import retrofit2.Response

interface NewsRemoteDataSource {

    suspend fun getTopHeadLine(): Response<NewsAPIResponse>

}