package com.example.newscatcherapp.repository

import com.example.newscatcherapp.database.SavedArticle
import com.example.newscatcherapp.database.SavedArticleDatabase

class DataBaseRepository(private val savedArticleDatabase: SavedArticleDatabase) {

    suspend fun insertIntoDatabase(list: SavedArticle) {
        savedArticleDatabase.articleDao().insert(list)
    }

    suspend fun getFromDatabase(): List<SavedArticle> {
        return savedArticleDatabase.articleDao().getAll()
    }

    suspend fun deleteFromDatabase(link: String){
       savedArticleDatabase.articleDao().delete(link = link)
    }

    fun checkAlreadyExists(link: String): Boolean {
       return savedArticleDatabase.articleDao().checkAlreadySaved(link)
    }


}