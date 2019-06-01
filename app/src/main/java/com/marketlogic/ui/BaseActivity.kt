package com.marketlogic.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marketlogic.BaseApp
import com.marketlogic.viewmodel.ViewModelFactory
import javax.inject.Inject

abstract  class BaseActivity : AppCompatActivity() {
    @Inject  lateinit var viewModelFactory: ViewModelFactory

    abstract  fun getLayoutById(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutById())
        configureDagger()
        initUI()
    }

    abstract fun initUI()

    private  fun configureDagger() = (this.application as BaseApp).appComponent.inject(this)
}