package com.keep_updated.covidhelper.presentation.viewModel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keep_updated.covidhelper.data.models.NewsAPIResponse
import com.keep_updated.covidhelper.data.util.Resource
import com.keep_updated.covidhelper.domain.usecase.GetNewsHeadlineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsHeadLineUseCase: GetNewsHeadlineUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _newsHeadLines: MutableLiveData<Resource<NewsAPIResponse>> = MutableLiveData()
    val newsHeadLines: LiveData<Resource<NewsAPIResponse>> = _newsHeadLines


    fun getNewsHeadLines() = viewModelScope.launch {
        _newsHeadLines.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(context)) {

                val apiResult = getNewsHeadLineUseCase.execute()
                _newsHeadLines.postValue(apiResult)
            } else {
                _newsHeadLines.postValue(Resource.Error("Internet is not Available"))
            }
        } catch (e: Exception) {
            _newsHeadLines.postValue(Resource.Error(e.message.toString()))
        }

    }


    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false

    }


}