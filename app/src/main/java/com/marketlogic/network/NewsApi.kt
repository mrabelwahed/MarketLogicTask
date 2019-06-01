package com.marketlogic.network

import com.marketlogic.data.NewsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApi {

    @GET("v2/top-headlines")
    fun getNewsList(@Query("country") country: String, @Query("apiKey") apiKey: String):
            Observable<NewsResponse>

}