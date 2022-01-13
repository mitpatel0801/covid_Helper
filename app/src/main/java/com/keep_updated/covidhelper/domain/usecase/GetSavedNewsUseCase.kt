package com.keep_updated.covidhelper.domain.usecase

import androidx.lifecycle.LiveData
import com.keep_updated.covidhelper.data.models.Article
import com.keep_updated.covidhelper.domain.repository.NewsRepository

class GetSavedNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(): LiveData<List<Article>> = newsRepository.getSavedNews()

}