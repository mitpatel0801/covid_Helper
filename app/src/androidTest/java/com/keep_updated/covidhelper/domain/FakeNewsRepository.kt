package com.keep_updated.covidhelper.domain


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.keep_updated.covidhelper.data.models.Article
import com.keep_updated.covidhelper.data.models.NewsAPIResponse
import com.keep_updated.covidhelper.data.util.Resource
import com.keep_updated.covidhelper.domain.repository.NewsRepository

class FakeNewsRepository : NewsRepository {

    private val articles = mutableListOf<Article>()
    private val observableArticles = MutableLiveData<List<Article>>(articles)

    override suspend fun getNewsHeadlines(page: Int): Resource<NewsAPIResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getSearchNews(page: Int, query: String): Resource<NewsAPIResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun saveNews(article: Article) {
        articles.add(article)
        observableArticles.postValue(articles)
    }

    override suspend fun deleteNews(article: Article) {
        articles.remove(article)
        observableArticles.postValue(articles)
    }

    override fun getSavedNews(): LiveData<List<Article>> = observableArticles

}