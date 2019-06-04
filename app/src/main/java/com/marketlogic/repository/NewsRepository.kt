package com.marketlogic.repository

import com.marketlogic.API_KEY
import com.marketlogic.COUNTRY
import com.marketlogic.PAGE_SIZE
import com.marketlogic.data.NewsResponse
import com.marketlogic.network.NewsApi
import io.reactivex.Flowable

class NewsRepository (val newsApi: NewsApi){

    fun getNewsList(query:String,
                    country:String=COUNTRY,
                    apiKey:String= API_KEY,
                    page:Int,
                    pageSize:Int = PAGE_SIZE): Flowable<NewsResponse>{
        return  newsApi.getNewsList(query, country , apiKey ,page, pageSize)
    }


}