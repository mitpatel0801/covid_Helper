package com.keep_updated.covidhelper.presentation.fragments

import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.keep_updated.covidhelper.data.models.Article
import com.keep_updated.covidhelper.databinding.FragmentDetailedNewsBinding


class DetailedNewsFragment : Fragment() {

    private lateinit var fragmentDetailedNewsBinding: FragmentDetailedNewsBinding
    private lateinit var article: Article

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentDetailedNewsBinding = FragmentDetailedNewsBinding.inflate(inflater)
        return fragmentDetailedNewsBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: DetailedNewsFragmentArgs by navArgs()
        article = args.article

        fragmentDetailedNewsBinding.vwNews.webViewClient = WebViewClient()

        if (article.url.isNotEmpty()) {
            fragmentDetailedNewsBinding.vwNews.loadUrl(article.url)
        }

    }

    private fun hideProgressbar() {
        fragmentDetailedNewsBinding.progressBar.visibility = View.GONE
    }


    inner class WebViewClient : android.webkit.WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            if (Uri.parse(url).host.equals(article.url)) {
                return false
            } else {
                Snackbar.make(
                    fragmentDetailedNewsBinding.root,
                    "Sorry, We do not allow to open any Web Link",
                    Snackbar.LENGTH_SHORT
                ).show()
            }

            return true
        }

        override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            hideProgressbar()
        }
    }
}
