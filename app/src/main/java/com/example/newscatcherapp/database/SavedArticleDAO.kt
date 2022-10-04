package com.example.newscatcherapp.database

import androidx.room.*
import java.nio.charset.CodingErrorAction.REPLACE

@Dao
interface SavedArticleDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: SavedArticle)
    @Query("delete from SavedArticle where link=:link")
    suspend fun delete(link: String)
    @Query("select * from SavedArticle")
    suspend fun getAll(): List<SavedArticle>
    @Query("select exists (select 1 from SavedArticle WHERE link = :link)")
    fun checkAlreadySaved(link: String): Boolean
}