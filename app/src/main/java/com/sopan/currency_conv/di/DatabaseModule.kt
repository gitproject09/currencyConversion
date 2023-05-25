package com.sopan.currency_conv.di

import com.sopan.currency_conv.db.SupportedCurrenciesDao
import com.sopan.currency_conv.db.CurrencyConvDatabase
import android.app.Application
import androidx.room.Room
import com.sopan.currency_conv.db.CurrencyRateDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application): CurrencyConvDatabase {
       return Room.databaseBuilder(application, CurrencyConvDatabase::class.java, "countries")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideCurrencyListDao(database: CurrencyConvDatabase): SupportedCurrenciesDao {
        return  database.countriesDao
    }
    fun provideCurrencyRateDao(database: CurrencyConvDatabase): CurrencyRateDao {
        return  database.currencyRateDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideCurrencyListDao(get()) }
    single { provideCurrencyRateDao(get()) }


}
