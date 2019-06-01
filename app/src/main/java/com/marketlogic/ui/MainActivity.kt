package com.marketlogic.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.marketlogic.NEWS_DETAILS_KEY
import com.marketlogic.R
import com.marketlogic.viewmodel.NewsviewModel


class MainActivity : BaseActivity() {
    private lateinit var viewModel: NewsviewModel

    override fun getLayoutById() = R.layout.activity_main
    private val newsListFragment = NewsListFragment()
    private val pokemonDetailsFragment = PokemonDetailsFragment()

    override fun initUI() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[NewsviewModel::class.java]
        supportFragmentManager.beginTransaction().add(R.id.container, newsListFragment).commit()
    }

    fun observePokemonList() {
        viewModel.getLiveNews().observe(this, Observer {
            newsListFragment.setData(it)
        })
    }


    fun getPokemonList() {
        viewModel.getPokemonList()
    }

    fun getPokemonDetails(id: Int) {

        val bundle = Bundle()
        bundle.putInt(NEWS_DETAILS_KEY, id)
        pokemonDetailsFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, pokemonDetailsFragment)
            .addToBackStack(null)
            .commit()

    }

    fun getViewModel() = viewModel


}
