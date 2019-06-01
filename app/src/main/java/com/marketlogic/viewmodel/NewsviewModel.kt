package com.marketlogic.viewmodel

import androidx.lifecycle.MutableLiveData
import com.marketlogic.data.NewsResponse
import com.marketlogic.domain.NewsUsecase
import javax.inject.Inject

class NewsviewModel @Inject constructor(private val usecase: NewsUsecase) : BaseViewModel() {

    private val newsListMutableLiveData = MutableLiveData<NewsResponse>()

    fun getPokemonList() {
        if (newsListMutableLiveData.value != null) {
            return
        }
        val disposable = usecase.getNewsList()
            .subscribe {
                newsListMutableLiveData.value = it
            }
        compositeDisposable.add(disposable)
    }



    fun getLiveNews() = newsListMutableLiveData


}