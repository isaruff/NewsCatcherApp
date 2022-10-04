package com.example.newscatcherapp.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "SavedArticle", indices = [Index(value=["link"], unique = true)])
@Parcelize
data class SavedArticle(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val title: String?,
    val author: String?,
    val published_date: String?,
    val summary: String?,
    val topic: String?,
    val media: String?,
    val link: String?,
    val excerpt: String?
) : Parcelable