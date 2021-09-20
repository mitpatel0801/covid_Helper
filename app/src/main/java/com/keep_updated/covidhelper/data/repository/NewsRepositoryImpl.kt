package com.keep_updated.covidhelper.data.repository

import com.keep_updated.covidhelper.data.models.Article
import com.keep_updated.covidhelper.data.models.NewsAPIResponse
import com.keep_updated.covidhelper.data.repository.dataSource.NewsRemoteDataSource
import com.keep_updated.covidhelper.data.util.Resource
import com.keep_updated.covidhelper.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(private val newsRemoteDataSource: NewsRemoteDataSource) : NewsRepository {

    override suspend fun getNewsHeadlines(): Resource<NewsAPIResponse> {
        return responseToResource(newsRemoteDataSource.getTopHeadLine())
    }

    private fun responseToResource(response: Response<NewsAPIResponse>): Resource<NewsAPIResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun getSearchNews(searchQuery: String): Resource<NewsAPIResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun saveNews(article: Article) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNews(article: Article) {
        TODO("Not yet implemented")
    }

    override fun getSavedNews(): Flow<List<Article>> {
        TODO("Not yet implemented")
    }
}