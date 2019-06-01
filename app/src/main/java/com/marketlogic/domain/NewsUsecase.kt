package com.marketlogic.domain

import com.marketlogic.repository.NewsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NewsUsecase(private val repository: NewsRepository) {

    fun getNewsList() = repository.getNewsList()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

   }