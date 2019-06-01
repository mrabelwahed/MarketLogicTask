package com.marketlogic.di

import com.marketlogic.di.module.NetworkModule
import com.marketlogic.di.module.PokemonUsecaseModule
import com.marketlogic.di.module.RepositoryModule
import com.marketlogic.di.module.ViewModelModule
import com.marketlogic.ui.BaseActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
 NetworkModule::class,
 ViewModelModule::class,
 PokemonUsecaseModule::class,
 RepositoryModule::class])
interface BaseComponent {
 fun inject(baseActivity: BaseActivity)
}