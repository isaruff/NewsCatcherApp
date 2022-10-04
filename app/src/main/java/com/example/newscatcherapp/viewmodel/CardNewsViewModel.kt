package com.example.newscatcherapp.viewmodel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newscatcherapp.api.RetrofitInstance
import com.example.newscatcherapp.model.ArticleData
import com.example.newscatcherapp.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber

class CardNewsViewModel : ViewModel() {

    private val repository by lazy {
        NewsRepository(RetrofitInstance.api)
    }

    val cardNewsLiveData : MutableLiveData<List<ArticleData>> = MutableLiveData(emptyList())

    fun getCardNews(search: String = "Today", lang: String) {
        if (cardNewsLiveData.value?.isEmpty() == true) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = repository.getResponseData(search, lang)
                    cardNewsLiveData.postValue(response.body()?.articles)
                } catch(e: IOException) {
                    Timber.i("Internet Connection is needed")
                } catch(e: HttpException) {
                    Timber.i("Unexpected Error")
                }

            }
        }
    }


}