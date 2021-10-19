package com.keep_updated.covidhelper.data.repository.dataSource

import com.keep_updated.covidhelper.data.models.Article

interface NewsLocalDataSource {
    suspend fun saveNews(article: Article)
}