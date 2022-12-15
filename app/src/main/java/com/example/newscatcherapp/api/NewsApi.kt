package com.example.newscatcherapp.api

import com.example.newscatcherapp.API_KEY
import com.example.newscatcherapp.model.NewsResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface NewsApi {

    @GET("/v2/search?")
    suspend fun getData(
        @Header("x-api-key") key: String = API_KEY,
        @Query("q") search : String,
        @Query("lang") lang : String

    ): Response<NewsResponseData>
}