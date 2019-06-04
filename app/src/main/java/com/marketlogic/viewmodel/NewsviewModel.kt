package com.marketlogic.viewmodel

import com.marketlogic.data.NewsResponse
import com.marketlogic.domain.NewsUsecase
import io.reactivex.Flowable
import javax.inject.Inject

class NewsviewModel @Inject constructor(private val usecase: NewsUsecase) : BaseViewModel() {

    fun getNewsList(query: String, country: String, apiKey: String, page: Int, pageSize: Int): Flowable<NewsResponse> {
        return usecase.getNewsList(query, country, apiKey, page, pageSize)
    }

}