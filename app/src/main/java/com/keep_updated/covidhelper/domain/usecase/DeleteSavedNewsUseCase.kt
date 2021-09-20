package com.keep_updated.covidhelper.domain.usecase

import com.keep_updated.covidhelper.data.models.Article
import com.keep_updated.covidhelper.domain.repository.NewsRepository

class DeleteSavedNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(article: Article) = newsRepository.deleteNews(article)
}