package com.keep_updated.covidhelper.domain.usecase

import com.keep_updated.covidhelper.domain.repository.NewsRepository

class DeleteSavedNewsUseCase(private val newsRepository: NewsRepository) {
}