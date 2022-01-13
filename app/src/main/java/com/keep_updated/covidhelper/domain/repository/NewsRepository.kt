package com.keep_updated.covidhelper.domain.repository

import androidx.lifecycle.LiveData
import com.keep_updated.covidhelper.data.models.Article
import com.keep_updated.covidhelper.data.models.NewsAPIResponse
import com.keep_updated.covidhelper.data.util.Resource

interface NewsRepository {

    suspend fun getNewsHeadlines(page: Int): Resource<NewsAPIResponse>
    suspend fun getSearchNews(page: Int, query: String): Resource<NewsAPIResponse>
    suspend fun saveNews(article: Article)
    suspend fun deleteNews(article: Article)
    fun getSavedNews(): LiveData<List<Article>>
}