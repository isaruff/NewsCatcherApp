package com.example.newscatcherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newscatcherapp.database.SavedArticle
import com.example.newscatcherapp.database.SavedArticleDatabase
import com.example.newscatcherapp.model.ArticleData
import com.example.newscatcherapp.repository.DataBaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExpandedNewsViewModel(application: Application) : AndroidViewModel(application) {

    val stateSaved: MutableLiveData<Boolean> = MutableLiveData()


    private val repository by lazy {
        DataBaseRepository(
            SavedArticleDatabase.getInstance(
                getApplication()
            )
        )
    }

    fun insertIntoDatabase(list: SavedArticle) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertIntoDatabase(list)
        }
    }

    fun checkAlreadyExist(link: String) {
        viewModelScope.launch(Dispatchers.IO) {
            stateSaved.postValue(repository.checkAlreadyExists(link))
        }
    }

    fun deleteSavedArticle(link: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFromDatabase(link)
        }
    }
}