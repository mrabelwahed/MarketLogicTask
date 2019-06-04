package com.marketlogic.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marketlogic.R
import com.marketlogic.data.Article
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list.view.*
import java.text.SimpleDateFormat
import java.util.*
import android.util.DisplayMetrics
import android.content.res.Resources


class NewsListAdapter : RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>() {
    val newsList = ArrayList<Article>()
    lateinit var listener: OnClickListener
    lateinit var context:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_list, null)
        return NewsViewHolder(view)
    }

    override fun getItemCount() = newsList.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindNews(newsList[position])
        holder.itemView.setOnClickListener {
            listener.onClick(position, it)
        }
    }


    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val newsImage = view.newsImage
        fun bindNews(article: Article) {
            itemView.newsTitle.text = article.title
            itemView.newsDate.text = formateDate(article.publishedAt)
            val width = Resources.getSystem().getDisplayMetrics().widthPixels

            Picasso.get().load(article.urlToImage).resize(width,width/2).placeholder(com.marketlogic.R.drawable.ic_image_black_48dp)
                .into(newsImage)
        }

        fun formateDate(dateString: String): String {
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
            val date = format.parse(dateString)
            val dateFormatter = SimpleDateFormat("dd MMM , yyyy")
            return dateFormatter.format(date)
        }
    }

    fun addNews(list: ArrayList<Article>) {
        newsList.addAll(list)
        notifyDataSetChanged()
    }

    fun clear(){
        newsList.clear()
        notifyDataSetChanged()
    }

    fun setClickListener(listener: OnClickListener) {
        this.listener = listener
    }

}


