package com.sopan.currency_conv.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sopan.currency_conv.model.CurrencyRate
import com.sopan.currency_conv.model.SupportedCurrency

@Database(
    entities = [CurrencyRate::class, SupportedCurrency::class],
    version = 1, exportSchema = false
)

abstract class CurrencyConvDatabase : RoomDatabase() {
    abstract val countriesDao: SupportedCurrenciesDao
    abstract val currencyRateDao: CurrencyRateDao
}