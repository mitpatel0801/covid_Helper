package com.keep_updated.covidhelper.data.repository

import com.keep_updated.covidhelper.data.models.Article
import com.keep_updated.covidhelper.data.models.NewsAPIResponse
import com.keep_updated.covidhelper.data.repository.dataSource.NewsLocalDataSource
import com.keep_updated.covidhelper.data.repository.dataSource.NewsRemoteDataSource
import com.keep_updated.covidhelper.data.util.Resource
import com.keep_updated.covidhelper.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val localDataSource: NewsLocalDataSource
) : NewsRepository {

    override suspend fun getNewsHeadlines(page: Int): Resource<NewsAPIResponse> {
        return responseToResource(newsRemoteDataSource.getTopHeadLine(page))
    }

    override suspend fun getSearchNews(
        page: Int,
        query: String
    ): Resource<NewsAPIResponse> {
        return responseToResource(newsRemoteDataSource.getSearchedNews(page, query))
    }

    override suspend fun saveNews(article: Article) {
        localDataSource.saveNews(article)
    }

    override suspend fun deleteNews(article: Article) {
        TODO("Not yet implemented")
    }

    override fun getSavedNews(): Flow<List<Article>> {
        TODO("Not yet implemented")
    }

    private fun responseToResource(response: Response<NewsAPIResponse>): Resource<NewsAPIResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

}