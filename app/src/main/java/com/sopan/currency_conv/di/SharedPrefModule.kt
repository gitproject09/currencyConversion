package com.sopan.currency_conv.di

import com.sopan.currency_conv.db.SharedPref
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sharedPrefModule = module {

    single { SharedPref(androidContext()) }

}