package com.example.newscatcherapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

@Database(entities = [SavedArticle::class], version = 1)
abstract class SavedArticleDatabase:RoomDatabase() {
    abstract fun articleDao():SavedArticleDAO
    companion object {
        var INSTANCE: SavedArticleDatabase? = null
        fun getInstance(context : Context): SavedArticleDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    SavedArticleDatabase::class.java,
                    "SavedArticle.db").build()
            }
            return INSTANCE!!
        }
    }
}