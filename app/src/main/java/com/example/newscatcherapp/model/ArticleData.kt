package com.example.newscatcherapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newscatcherapp.database.SavedArticle
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticleData(
    val title: String?,
    val author: String?,
    val published_date: String?,
    val summary: String?,
    val topic: String?,
    val media: String?,
    val _id: String?,
    val link: String?,
    val excerpt: String?,
) : Parcelable {
    fun toSaveArticle() = SavedArticle(
        null,
        title,
        author,
        published_date,
        summary,
        topic,
        media,
        link,
        excerpt,
    )
}


