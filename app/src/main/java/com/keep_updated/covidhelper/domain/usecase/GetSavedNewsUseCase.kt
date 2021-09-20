package com.keep_updated.covidhelper.domain.usecase

import com.keep_updated.covidhelper.data.models.Article
import com.keep_updated.covidhelper.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(): Flow<List<Article>> = newsRepository.getSavedNews()

}