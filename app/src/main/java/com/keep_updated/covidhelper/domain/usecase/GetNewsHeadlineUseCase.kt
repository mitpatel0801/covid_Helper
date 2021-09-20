package com.keep_updated.covidhelper.domain.usecase

import com.keep_updated.covidhelper.data.models.NewsAPIResponse
import com.keep_updated.covidhelper.data.util.Resource
import com.keep_updated.covidhelper.domain.repository.NewsRepository

class GetNewsHeadlineUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(): Resource<NewsAPIResponse> = newsRepository.getNewsHeadlines()
}