package com.keep_updated.covidhelper.data.repository.dataSource

import androidx.lifecycle.LiveData
import com.keep_updated.covidhelper.data.models.Article

interface NewsLocalDataSource {
    suspend fun saveNews(article: Article)
    fun getSavedNews(): LiveData<List<Article>>
    suspend fun deleteArticle(article: Article)
}