package com.keep_updated.covidhelper.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.keep_updated.covidhelper.data.models.Article
import com.keep_updated.covidhelper.databinding.NewsListItemBinding


class NewsAdapter(private val onClickListener: (Article) -> Unit) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private val list: ArrayList<Article> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun addAllArticle(newList: List<Article>) {
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun clearList() {
        list.clear()
    }

    inner class ViewHolder(private val newsListItemBinding: NewsListItemBinding) :
        RecyclerView.ViewHolder(newsListItemBinding.root) {

        fun bind(article: Article) {
            newsListItemBinding.tvTitle.text = article.title
            newsListItemBinding.tvDescription.text = article.description
            newsListItemBinding.tvPublishedAt.text = article.publishedAt
            //  newsListItemBinding.tvSource.text = article.source.name

            newsListItemBinding.root.setOnClickListener {
                onClickListener(article)
            }
            Glide.with(newsListItemBinding.ivArticleImage.context)
                .load(article.urlToImage)
                .into(newsListItemBinding.ivArticleImage)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val newsListItemBinding =
            NewsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(newsListItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = list[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int = list.size

}