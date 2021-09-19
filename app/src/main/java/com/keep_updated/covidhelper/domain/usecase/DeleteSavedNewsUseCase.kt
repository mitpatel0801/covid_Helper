package com.keep_updated.covidhelper.domain.usecase

import com.keep_updated.covidhelper.data.Article
import com.keep_updated.covidhelper.data.NewsAPIResponse
import com.keep_updated.covidhelper.data.util.Resource
import com.keep_updated.covidhelper.domain.repository.NewsRepository

class DeleteSavedNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(article: Article) = newsRepository.deleteNews(article)
}