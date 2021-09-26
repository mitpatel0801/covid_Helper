package com.keep_updated.covidhelper.presentation.activities

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.keep_updated.covidhelper.R
import com.keep_updated.covidhelper.presentation.viewModel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newsViewModel.getNewsHeadLines()
        newsViewModel.newsHeadLines.observe(this) {
            findViewById<TextView>(R.id.abcd).text = it.data.toString()

        }

    }
}