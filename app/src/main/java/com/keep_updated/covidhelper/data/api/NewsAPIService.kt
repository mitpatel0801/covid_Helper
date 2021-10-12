package com.keep_updated.covidhelper.data.api

import com.keep_updated.covidhelper.BuildConfig
import com.keep_updated.covidhelper.data.models.NewsAPIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIService {

    @GET("v2/top-headlines?q=covid&language=en")
    suspend fun getTopHeadlines(
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
    ): Response<NewsAPIResponse>

}