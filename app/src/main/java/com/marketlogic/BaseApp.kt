package com.marketlogic

import android.app.Application
import com.marketlogic.di.BaseComponent
import com.marketlogic.di.DaggerBaseComponent
import com.marketlogic.di.module.NetworkModule
import com.marketlogic.di.module.PokemonUsecaseModule
import com.marketlogic.di.module.RepositoryModule

class BaseApp : Application() {
    lateinit var appComponent:BaseComponent

    override fun onCreate() {
        super.onCreate()
        this.appComponent = this.initDagger()
    }



    private fun initDagger()  = DaggerBaseComponent.builder()
        .networkModule(NetworkModule())
        .repositoryModule(RepositoryModule())
        .pokemonUsecaseModule(PokemonUsecaseModule())
        .build()
}