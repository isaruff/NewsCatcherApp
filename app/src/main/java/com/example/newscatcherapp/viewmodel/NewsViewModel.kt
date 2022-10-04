package com.example.newscatcherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newscatcherapp.api.RetrofitInstance
import com.example.newscatcherapp.repository.LanguagePreferences
import com.example.newscatcherapp.model.ArticleData
import com.example.newscatcherapp.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber

class NewsViewModel(application: Application): AndroidViewModel(application) {

    private val repository by lazy {
        NewsRepository(RetrofitInstance.api)
    }

    private val myLangPreference = LanguagePreferences(application)

    fun getLang(): String? {
        return myLangPreference.getLangSetting()
    }
    fun setLang(lang: String) {
        myLangPreference.setLangSetting(lang)
    }





    val newsLiveData : MutableLiveData<List<ArticleData>> = MutableLiveData()


    fun getNewsData(search: String, lang: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getResponseData(search, lang)
                newsLiveData.postValue(response.body()?.articles)
            } catch (e: IOException) {
                Timber.i("Internet Connection is needed")
            } catch (e: HttpException) {
                Timber.i("Unexpected Error")
            }
        }
    }





}