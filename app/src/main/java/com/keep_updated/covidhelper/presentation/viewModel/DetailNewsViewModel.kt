package com.keep_updated.covidhelper.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keep_updated.covidhelper.data.models.Article
import com.keep_updated.covidhelper.domain.usecase.SaveNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailNewsViewModel @Inject constructor(private val saveNewsUseCase: SaveNewsUseCase) :
    ViewModel() {

    fun saveNews(article: Article) = viewModelScope.launch {
        saveNewsUseCase.execute(article)
    }
}