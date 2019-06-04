package com.marketlogic.viewmodel

import androidx.lifecycle.MutableLiveData
import com.marketlogic.data.NewsResponse
import com.marketlogic.domain.NewsUsecase
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsviewModel @Inject constructor(private val usecase: NewsUsecase) : BaseViewModel() {

    val progressBarLoading = MutableLiveData<Boolean>()
    val newsResponse = MutableLiveData<NewsResponse>()
    val paginator = PublishProcessor.create<Int>()
    var loadMore = false


    fun getNewsList(query: String, country: String, apiKey: String, page: Int, pageSize: Int): Flowable<NewsResponse> {
        return usecase.getNewsList(query, country, apiKey, page, pageSize)
    }

    private fun subscribeForData(query: String, country: String, apiKey: String, page: Int, pageSize: Int) {
        val disposable = paginator
            .onBackpressureDrop()
            .doOnNext { page ->
                progressBarLoading.value = true
            }
            .concatMap { page ->
                getNewsList(query,country,apiKey,page,pageSize)
                    .subscribeOn(Schedulers.io())
                    .onErrorReturn {
                        // handle error
                        progressBarLoading.value = false
                        NewsResponse("error", arrayListOf())
                    }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                newsResponse.value = it
                if (it.articles.size < 20 || it.articles.isEmpty())
                    loadMore = false
                progressBarLoading.value = false
            }

        compositeDisposable.add(disposable)
        if (loadMore)
            paginator.onNext(page)
    }

}