package com.keep_updated.covidhelper.domain.usecase

import com.keep_updated.covidhelper.data.Article
import com.keep_updated.covidhelper.data.NewsAPIResponse
import com.keep_updated.covidhelper.data.util.Resource
import com.keep_updated.covidhelper.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(): Flow<List<Article>> = newsRepository.getSavedNews()

}