package com.marketlogic.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.marketlogic.NEWS_DETAILS_KEY
import com.marketlogic.R
import com.marketlogic.data.Article
import com.marketlogic.data.NewsResponse
import com.marketlogic.viewmodel.NewsviewModel
import io.reactivex.Flowable


class MainActivity : BaseActivity() {
    private lateinit var viewModel: NewsviewModel

    override fun getLayoutById() = R.layout.activity_main
    private val newsListFragment = NewsListFragment()
    private val newsDetailsFragment = NewsDetailsFragment()

    override fun initUI() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[NewsviewModel::class.java]
        supportFragmentManager.beginTransaction().add(R.id.container, newsListFragment).commit()
    }

//    fun observeNewsList() {
//        viewModel.getLiveNews().observe(this, Observer {
//            newsListFragment.setData(it)
//        })
//    }




    fun getNewsDetails(article: Article) {

        val bundle = Bundle()
        bundle.putSerializable(NEWS_DETAILS_KEY, article)
        newsDetailsFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, newsDetailsFragment)
            .addToBackStack(null)
            .commit()

    }

    fun getViewModel() = viewModel


}
