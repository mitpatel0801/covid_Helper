package com.keep_updated.covidhelper.domain.repository

import com.keep_updated.covidhelper.data.models.Article
import com.keep_updated.covidhelper.data.models.NewsAPIResponse
import com.keep_updated.covidhelper.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getNewsHeadlines():Resource<NewsAPIResponse>
    suspend fun getSearchNews(searchQuery:String):Resource<NewsAPIResponse>
    suspend fun saveNews(article: Article)
    suspend fun deleteNews(article: Article)
    fun getSavedNews(): Flow<List<Article>>
}