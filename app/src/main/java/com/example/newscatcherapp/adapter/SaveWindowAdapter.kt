package com.example.newscatcherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.newscatcherapp.database.SavedArticle
import com.example.newscatcherapp.databinding.ItemNewsBinding

class SaveWindowAdapter(private val onClick: (SavedArticle) -> Unit) :
    ListAdapter<SavedArticle, SaveWindowAdapter.SaveWindowViewHolder>(GenericDiffCallback<SavedArticle>()) {


    class SaveWindowViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaveWindowViewHolder {
        return SaveWindowViewHolder(
            ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SaveWindowViewHolder, position: Int) {
        holder.binding.apply {
            val singleNews = currentList[position]
            topicView.text = singleNews.topic
            headlineView.text = singleNews.title
            authorView.text = singleNews.author
            dateView.text = singleNews.published_date!!.subSequence(0, 10)
            newsImageView.load(singleNews.media)
            root.setOnClickListener {
                onClick(singleNews)
            }
        }
    }

}