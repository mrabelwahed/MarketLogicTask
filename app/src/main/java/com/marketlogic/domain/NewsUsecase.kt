package com.marketlogic.domain

import com.marketlogic.repository.NewsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NewsUsecase(private val repository: NewsRepository) {

    fun getNewsList(
        query: String, country: String, apiKey: String,
        page: Int, pageSize: Int) = repository.getNewsList(query, country, apiKey, page, pageSize)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

}