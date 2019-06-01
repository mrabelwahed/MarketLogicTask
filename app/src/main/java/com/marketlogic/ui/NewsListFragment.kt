package com.marketlogic.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.marketlogic.R
import com.marketlogic.data.NewsResponse
import kotlinx.android.synthetic.main.fragment_pokemon_list.*

class NewsListFragment : Fragment() , OnClickListener{
    override fun onClick(position: Int, view: View) {
        getPokemonDetails(position+1)
    }

    val newsListAdapter = NewsListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pokemon_list,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(view)
        getNewsList()
    }

     fun initUI(view : View){
        val linearLayoutManager = LinearLayoutManager(context)
         newsListAdapter.setClickListener(this)
         newsList.apply {
             layoutManager = linearLayoutManager
             addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
             adapter = newsListAdapter
         }

    }

    fun getNewsList(){
        (activity as MainActivity).getPokemonList()
        (activity as MainActivity).observePokemonList()
    }

    fun setData(response: NewsResponse?) {
        response?.articles?.let { newsListAdapter.addPokmons(it) }
    }

    fun getPokemonDetails(position:Int){
        (activity as MainActivity).getPokemonDetails(position)
    }


}
