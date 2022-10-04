package com.example.newscatcherapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    val api: NewsApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.newscatcherapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }
}