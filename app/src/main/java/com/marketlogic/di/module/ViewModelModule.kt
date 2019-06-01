package com.marketlogic.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marketlogic.di.ViewModelKey
import com.marketlogic.viewmodel.NewsviewModel
import com.marketlogic.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(NewsviewModel::class)
    internal abstract fun postMainViewModel(viewModel: NewsviewModel): ViewModel

}