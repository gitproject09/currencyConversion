package com.sopan.currency_conv.app

import androidx.multidex.MultiDexApplication
import com.sopan.currency_conv.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class CurrencyApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initiateKoin()
    }
    private fun initiateKoin() {
        startKoin{
            androidContext(this@CurrencyApp)
            modules(provideDependency())
        }
    }

    open fun provideDependency() = appComponent

}