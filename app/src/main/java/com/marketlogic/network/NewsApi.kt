package com.marketlogic.network

import com.marketlogic.data.NewsResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApi {

    @GET("v2/top-headlines")
    fun getNewsList(
        @Query("q") query: String,
        @Query("country") country: String,
        @Query("apiKey") apiKey: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ):
            Flowable<NewsResponse>

}