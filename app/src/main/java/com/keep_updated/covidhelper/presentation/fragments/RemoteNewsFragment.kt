package com.keep_updated.covidhelper.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.keep_updated.covidhelper.R
import com.keep_updated.covidhelper.data.util.Resource
import com.keep_updated.covidhelper.databinding.FragmentRemoteNewsBinding
import com.keep_updated.covidhelper.presentation.adapters.NewsAdapter
import com.keep_updated.covidhelper.presentation.viewModel.RemoteNewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RemoteNewsFragment : Fragment() {

    private val remoteNewsViewModel: RemoteNewsViewModel by viewModels()
    private lateinit var fragmentRemoteNewsBinding: FragmentRemoteNewsBinding
    private lateinit var adapter: NewsAdapter
    private var pageRemote = 1
    private var pagesRemote = 0

    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var isSearchedNews = false

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
            val action = RemoteNewsFragmentDirections.actionRemoteNewsItemToDetailedNewsFragment(it)
            findNavController().navigate(
                action
            )
        }

        initRecycleView()
        displayRemoteNews()
        setSearchView()
    }

    //Displaying news from remote data source
    private fun displayRemoteNews() {
        remoteNewsViewModel.getNewsHeadLines(pageRemote)
        remoteNewsViewModel.newsHeadLines.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    hideProgressbar()

                    resource.data?.let {
                        if (isSearchedNews) {
                            adapter.clearList()
                        }
                        isSearchedNews = false

                        adapter.addAllArticle(it.articles)
                        pagesRemote = if (it.totalResults % 20 == 0) {
                            it.totalResults / 20
                        } else {
                            it.totalResults / 20 + 1
                        }
                        isLastPage = (pageRemote == pagesRemote)
                    }
                }

                is Resource.Error -> {
                    hideProgressbar()
                    resource.message?.let {
                        Snackbar.make(
                            fragmentRemoteNewsBinding.root,
                            it,
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
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
        if (!isSearchedNews) {
            fragmentRemoteNewsBinding.rvNews.addOnScrollListener(onScrollListener)
        }
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
                pageRemote++
                remoteNewsViewModel.getNewsHeadLines(pageRemote)
                isScrolling = false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.show()
    }


    private fun setSearchView() {

        fragmentRemoteNewsBinding.searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    remoteNewsViewModel.getSearchedNews(1, p0.toString())
                    displaySearchedNews(fragmentRemoteNewsBinding.root)
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    MainScope().launch {
                        delay(2000)
                        remoteNewsViewModel.getSearchedNews(1, p0.toString())
                        displaySearchedNews(fragmentRemoteNewsBinding.root)
                    }
                    return false
                }
            }
        )

        fragmentRemoteNewsBinding.searchView.setOnCloseListener {
            initRecycleView()
            displayRemoteNews()
            return@setOnCloseListener false
        }
    }

    private fun displaySearchedNews(view: View) {

        remoteNewsViewModel.searchedResult.observe(viewLifecycleOwner) { resource ->
            when (resource) {

                is Resource.Success -> {
                    if (!isSearchedNews) {
                        pageRemote = 1
                        pagesRemote = 0
                    }
                    hideProgressbar()
                    resource.data?.let {
                        isSearchedNews = true
                        adapter.clearList()
                        adapter.addAllArticle(it.articles)
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
}


