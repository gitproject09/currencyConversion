package com.sopan.currency_conv.di

import com.sopan.currency_conv.db.SupportedCurrenciesDao
import com.sopan.currency_conv.db.CurrencyConvDatabase
import android.content.Context
import androidx.room.Room
import com.sopan.currency_conv.db.CurrencyRateDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Context): CurrencyConvDatabase {
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

    single { provideDatabase(androidContext()) }
    single { provideCurrencyListDao(get()) }
    single { provideCurrencyRateDao(get()) }


}
