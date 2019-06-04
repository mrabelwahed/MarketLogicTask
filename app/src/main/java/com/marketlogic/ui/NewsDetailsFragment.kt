package com.marketlogic.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.marketlogic.NEWS_DETAILS_KEY
import com.marketlogic.R
import com.marketlogic.data.Article
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_news_details.*
import android.content.Intent
import android.net.Uri


class NewsDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article = arguments?.getSerializable(NEWS_DETAILS_KEY) as Article
        article?.let { getNewsDetails(it) }
    }

    fun getNewsDetails(article: Article) {
        if (isVisible) {
            setData(article)
        }
    }


    fun setData(article: Article) {
        Picasso.get().load(article.urlToImage).placeholder(R.drawable.ic_image_black_48dp).into(newsImage)
        newsTitle.text = article.title
        newsDescription.text = article.description?:"No Description"
        newsContent.text = article.content?:"No Content"

        if (article.url.isNullOrEmpty())
            newsSource.visibility = View.GONE

        newsSource.setOnClickListener{
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(article.url)
            )
            startActivity(browserIntent)
        }
    }


}