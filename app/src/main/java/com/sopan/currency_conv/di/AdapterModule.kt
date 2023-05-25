package com.sopan.currency_conv.di

import com.sopan.currency_conv.views.adapter.SupportedCurrencyAdapter
import org.koin.dsl.module

val adapterModule = module {

    factory { SupportedCurrencyAdapter() }

}