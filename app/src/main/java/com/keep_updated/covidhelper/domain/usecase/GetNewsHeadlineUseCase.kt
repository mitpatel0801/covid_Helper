package com.keep_updated.covidhelper.domain.usecase

import com.keep_updated.covidhelper.domain.repository.NewsRepository

class GetNewsHeadlineUseCase(private val newsRepository: NewsRepository) {
}