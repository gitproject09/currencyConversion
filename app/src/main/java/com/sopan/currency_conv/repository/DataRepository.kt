package com.sopan.currency_conv.repository

import com.sopan.currency_conv.model.CurrencyRate
import com.sopan.currency_conv.model.CurrencyRateToDisplay
import com.sopan.currency_conv.model.SupportedCurrency
import com.sopan.currency_conv.util.AppResult

interface DataRepository {
    suspend fun getSupportedCurrencies(): AppResult<List<SupportedCurrency>?>
    suspend fun getSupportedCurrenciesFromApi(): AppResult<List<SupportedCurrency>?>
    suspend fun getCurrencyRate(): AppResult<List<CurrencyRateToDisplay>>
    suspend fun getCurrencyRateFromApi(): AppResult<List<CurrencyRate>>
}
