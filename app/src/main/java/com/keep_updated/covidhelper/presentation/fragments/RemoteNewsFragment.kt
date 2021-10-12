package com.keep_updated.covidhelper.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.keep_updated.covidhelper.R
import com.keep_updated.covidhelper.data.util.Resource
import com.keep_updated.covidhelper.databinding.FragmentRemoteNewsBinding
import com.keep_updated.covidhelper.presentation.adapters.NewsAdapter
import com.keep_updated.covidhelper.presentation.viewModel.RemoteNewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RemoteNewsFragment : Fragment() {

    private val remoteNewsViewModel: RemoteNewsViewModel by viewModels()
    private lateinit var fragmentRemoteNewsBinding: FragmentRemoteNewsBinding
    private lateinit var adapter: NewsAdapter
    private var page = 1
    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var pages = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentRemoteNewsBinding = FragmentRemoteNewsBinding.inflate(inflater)
        return fragmentRemoteNewsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = NewsAdapter {
            val bundle = Bundle()
            bundle.putSerializable("ARTICLE_KEY", it)
        }
        initRecycleView()
        displayAllNews(view)
    }

    private fun displayAllNews(view: View) {
        remoteNewsViewModel.getNewsHeadLines(page)
        remoteNewsViewModel.newsHeadLines.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    hideProgressbar()

                    resource.data?.let {
                        adapter.addAllArticle(it.articles)
                        pages = if (it.totalResults % 20 == 0) {
                            it.totalResults / 20
                        } else {
                            it.totalResults / 20 + 1
                        }
                        isLastPage = page == pages
                    }
                }
                is Resource.Error -> {
                    hideProgressbar()
                    resource.message?.let { Snackbar.make(view, it, Snackbar.LENGTH_LONG).show() }
                }
                is Resource.Loading -> {
                    showProgressbar()
                }
            }
        }
    }

    private fun initRecycleView() {
        fragmentRemoteNewsBinding.rvNews.layoutManager = LinearLayoutManager(context)
        fragmentRemoteNewsBinding.rvNews.adapter = adapter
        fragmentRemoteNewsBinding.rvNews.addOnScrollListener(onScrollListener)
    }

    private fun hideProgressbar() {
        isLoading = false
        fragmentRemoteNewsBinding.frameLayout.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.gray
            )
        )
        fragmentRemoteNewsBinding.progressBar.visibility = View.GONE
    }

    private fun showProgressbar() {
        isLoading = true
        fragmentRemoteNewsBinding.frameLayout.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )
        fragmentRemoteNewsBinding.progressBar.visibility = View.VISIBLE
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }

        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager =
                fragmentRemoteNewsBinding.rvNews.layoutManager as LinearLayoutManager
            val sizeOfTheCurrentList = layoutManager.itemCount
            val visibleItems = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()

            val hasReachedToEnd = topPosition + visibleItems >= sizeOfTheCurrentList
            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling
            if (shouldPaginate) {
                page++
                remoteNewsViewModel.getNewsHeadLines(page)
                isScrolling = false
            }
        }
    }
}


