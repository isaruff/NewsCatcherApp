package com.example.newscatcherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.newscatcherapp.databinding.ItemTodaysNewsBinding
import com.example.newscatcherapp.model.ArticleData

class TodaysNewsAdapter(private val onClick: (ArticleData) -> Unit) :
    ListAdapter<ArticleData, TodaysNewsAdapter.TodaysNewsViewHolder>(
        GenericDiffCallback<ArticleData>()
    ) {


    class TodaysNewsViewHolder(val binding: ItemTodaysNewsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodaysNewsViewHolder {
        return TodaysNewsViewHolder(
            ItemTodaysNewsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TodaysNewsViewHolder, position: Int) {
        holder.binding.apply {
            val cardNews = currentList[position]
            todaysTopicView.text = cardNews.topic
            todaysImageView.load(cardNews.media)
            todaysTitleView.text = cardNews.title

            root.setOnClickListener {
                onClick(cardNews)
            }
        }
    }


}