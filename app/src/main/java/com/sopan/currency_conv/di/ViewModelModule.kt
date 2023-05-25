package com.sopan.currency_conv.di

import com.sopan.currency_conv.viewmodel.DataViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    // Specific viewModel pattern to tell Koin how to build CountriesViewModel
    viewModel {
        DataViewModel(repository = get())
    }

}