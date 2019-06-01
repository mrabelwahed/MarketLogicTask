package com.marketlogic.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marketlogic.R
import com.marketlogic.data.Article
import com.marketlogic.data.Pokemon
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list.view.*

class NewsListAdapter : RecyclerView.Adapter<NewsListAdapter.PokemonViewHolder>() {
    val newsList = ArrayList<Article>()
    lateinit var listener: OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.item_list,null)
        return PokemonViewHolder(view)
    }

    override fun getItemCount() = newsList.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bindNews(newsList[position])
        holder.itemView.setOnClickListener {
         listener.onClick(position,it)
        }
    }


    class  PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bindNews(article: Article){
            itemView.newsTitle.text = article.title
            itemView.newsDate.text = article.publishedAt
            Picasso.get().load(article.urlToImage).into(itemView.newsImage)
        }
    }

    fun addPokmons(list: ArrayList<Article>){
        newsList.clear()
        newsList.addAll(list)
        notifyDataSetChanged()
    }

    fun setClickListener(listener: OnClickListener){
        this.listener = listener
    }

}


