package com.sopan.currency_conv.repository

import com.sopan.currency_conv.model.CurrencyRateResponse
import com.sopan.currency_conv.model.SupportedCurrenciesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyConvApi {

    @GET("/list")
    suspend fun getSupportedCountries(@Query("access_key") accessKey: String): Response<SupportedCurrenciesResponse>

    @GET("/live")
    suspend fun getCurrencyRate(@Query("access_key") accessKey: String): Response<CurrencyRateResponse>

}