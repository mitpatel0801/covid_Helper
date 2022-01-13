package com.keep_updated.covidhelper.data.repository.dataSourceImpl

import androidx.lifecycle.LiveData
import com.keep_updated.covidhelper.data.db.ArticleDao
import com.keep_updated.covidhelper.data.models.Article
import com.keep_updated.covidhelper.data.repository.dataSource.NewsLocalDataSource

class NewsLocalDataSourceImp(private val articleDao: ArticleDao) : NewsLocalDataSource {
    override suspend fun saveNews(article: Article) {
        return articleDao.insert(article)
    }

    override fun getSavedNews(): LiveData<List<Article>> = articleDao.getSavedNews()


    override suspend fun deleteArticle(article: Article) {
        articleDao.deleteSavedArticle(article)
    }
}