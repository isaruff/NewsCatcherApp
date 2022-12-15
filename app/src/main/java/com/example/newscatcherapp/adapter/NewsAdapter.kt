package com.example.newscatcherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.newscatcherapp.databinding.ItemNewsBinding
import com.example.newscatcherapp.model.ArticleData

class NewsAdapter(private val onClick: (ArticleData) -> Unit) :
    ListAdapter<ArticleData, NewsAdapter.NewsViewHolder>(GenericDiffCallback<ArticleData>()) {

    class NewsViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.binding.apply {
            val singleNews = currentList[position]
            topicView.text = singleNews.topic
            headlineView.text = singleNews.title
            authorView.text = singleNews.author
            dateView.text = singleNews.published_date?.subSequence(0, 10)
            newsImageView.load(singleNews.media)

            root.setOnClickListener {
                onClick(singleNews)
            }
        }
    }
}