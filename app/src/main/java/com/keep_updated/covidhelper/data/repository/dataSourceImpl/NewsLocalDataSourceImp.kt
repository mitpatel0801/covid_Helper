package com.keep_updated.covidhelper.data.repository.dataSourceImpl

import com.keep_updated.covidhelper.data.db.ArticleDao
import com.keep_updated.covidhelper.data.models.Article
import com.keep_updated.covidhelper.data.repository.dataSource.NewsLocalDataSource

class NewsLocalDataSourceImp(private val articleDao: ArticleDao) : NewsLocalDataSource {
    override suspend fun saveNews(article: Article) {
        return articleDao.insert(article)
    }
}