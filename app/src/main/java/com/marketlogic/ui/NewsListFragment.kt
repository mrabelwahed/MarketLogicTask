package com.marketlogic.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marketlogic.*
import com.marketlogic.data.Article
import com.marketlogic.data.NewsResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.concurrent.TimeUnit


class NewsListFragment : Fragment(), OnClickListener {

     val paginator = PublishProcessor.create<Int>()
    private val newsData = ArrayList<Article>()
    private val newsListAdapter = NewsListAdapter()
    private var query = ""
    private var page = 1
    private var totalItemCount = 0
    private var lastVisibleItem = 0
    private var loading = false
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var loadMore = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(view)
        getNewsList(query, COUNTRY, API_KEY,page, PAGE_SIZE)
        setupLoadMoreListener()
        subscribeForData()
    }

    private fun subscribeForData() {
        val disposable = paginator
            .onBackpressureDrop()
            .doOnNext { page ->
                this.page =page
                loading = true
                progressbar.visibility = View.VISIBLE
            }
            .concatMap { page ->
                dataFromNetwork(page)
                    .subscribeOn(Schedulers.io())
                    .onErrorReturn {
                        // handle error
                        loading = false
                        progressbar.visibility = View.INVISIBLE
                         NewsResponse("error", arrayListOf())
                    }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { items ->
                if(items.articles.size<20 || items.articles.isEmpty())
                    loadMore = false

                if (items.articles.isNotEmpty() && items.status != "error") {
                    newsData.addAll(items.articles)
                    newsListAdapter.addNews(items.articles)
                }
                loading = false
                progressbar.visibility = View.INVISIBLE
            }

        (activity as MainActivity).getViewModel().compositeDisposable.add(disposable)
        if (loadMore)
             paginator.onNext(page)
    }

    private fun dataFromNetwork(page: Int): Flowable<NewsResponse> {
        return  getNewsList(query, COUNTRY, API_KEY,page, PAGE_SIZE)
    }

    private fun setupLoadMoreListener() {
        newsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayoutManager.getItemCount()
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                if (!loading && totalItemCount <= lastVisibleItem + VISIBLE_THRESHOLD) {
                     page++
                    if (loadMore)
                         paginator.onNext(page)
                    loading = true
                }
            }
        })
    }

    @SuppressLint("CheckResult")
    fun initUI(view: View) {
        linearLayoutManager = LinearLayoutManager(context)
        newsListAdapter.setClickListener(this)
        newsList.apply {
            layoutManager = linearLayoutManager
            adapter = newsListAdapter
        }

        Observable.create(ObservableOnSubscribe<String> { subscriber ->
            searchBar.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(query: Editable?) {
                    subscriber.onNext(query?.toString())
                }

            })
        })
            .map { text -> text.toLowerCase().trim() }
            .debounce(250, TimeUnit.MILLISECONDS)
            .distinct()
            .filter { text -> text.isNotBlank() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text ->
                Log.d("TAG", "subscriber: $text")
                startSearch(text)
            }

    }

    fun getNewsList(query: String, country: String, apiKey: String,
        page: Int, pageSize: Int): Flowable<NewsResponse> {
        return (activity as MainActivity).getViewModel().getNewsList(query, country,apiKey,page,pageSize)
    }


    private fun getNewsDetails(article: Article) {
        (activity as MainActivity).getNewsDetails(article)
    }

    private fun startSearch(query: String) {
        this.page = 1
        newsData.clear()
        this.query = query
        newsListAdapter.clear()
        paginator.onNext(page)
    }

    override fun onClick(position: Int, view: View) {
        getNewsDetails(newsData[position])
    }
}
