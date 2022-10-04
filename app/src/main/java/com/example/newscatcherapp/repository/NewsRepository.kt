package com.example.newscatcherapp.repository

import com.example.newscatcherapp.api.NewsApi

class NewsRepository(private val newsApi: NewsApi) {

    suspend fun getResponseData(search : String, lang : String) = newsApi.getData(
        search = search, lang = lang )
}