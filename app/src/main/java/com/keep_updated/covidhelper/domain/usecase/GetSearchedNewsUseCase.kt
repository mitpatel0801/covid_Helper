package com.keep_updated.covidhelper.domain.usecase

import com.keep_updated.covidhelper.data.NewsAPIResponse
import com.keep_updated.covidhelper.data.util.Resource
import com.keep_updated.covidhelper.domain.repository.NewsRepository

class GetSearchedNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(searchQuery: String): Resource<NewsAPIResponse> =
        newsRepository.getSearchNews(searchQuery)

}