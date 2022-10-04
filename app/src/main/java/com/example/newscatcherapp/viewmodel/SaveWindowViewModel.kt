package com.example.newscatcherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newscatcherapp.database.SavedArticle
import com.example.newscatcherapp.database.SavedArticleDatabase
import com.example.newscatcherapp.repository.DataBaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SaveWindowViewModel(application: Application) : AndroidViewModel(application) {

    private val repository by lazy {
        DataBaseRepository(SavedArticleDatabase.getInstance(getApplication()))
    }

    val articleLiveData : MutableLiveData<List<SavedArticle>> = MutableLiveData()
    fun getSavedData() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getFromDatabase()
            articleLiveData.postValue(response)
            }


    }
}