package com.marketlogic.repository

import com.marketlogic.API_KEY
import com.marketlogic.COUNTRY
import com.marketlogic.LIMIT
import com.marketlogic.data.NewsResponse
import com.marketlogic.data.PokemonDetails
import com.marketlogic.data.PokemonResponse
import com.marketlogic.network.NewsApi
import io.reactivex.Observable

class NewsRepository (val newsApi: NewsApi){

    fun getNewsList(): Observable<NewsResponse>{
        return  newsApi.getNewsList(COUNTRY , API_KEY)
    }

}