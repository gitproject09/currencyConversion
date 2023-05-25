package com.sopan.currency_conv.di

import com.sopan.currency_conv.repository.CurrencyConvApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    fun provideCountriesApi(retrofit: Retrofit): CurrencyConvApi {
        return retrofit.create(CurrencyConvApi::class.java)
    }
    single{ provideCountriesApi(get()) }


}