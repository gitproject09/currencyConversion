package com.sopan.currency_conv.repository

import android.content.Context
import com.ente_kada.android.view.utilis.Logger
import com.sopan.currency_conv.BuildConfig
import com.sopan.currency_conv.db.CurrencyRateDao
import com.sopan.currency_conv.db.SharedPref
import com.sopan.currency_conv.db.SupportedCurrenciesDao
import com.sopan.currency_conv.model.CurrencyRate
import com.sopan.currency_conv.model.CurrencyRateToDisplay
import com.sopan.currency_conv.model.SupportedCurrency
import com.sopan.currency_conv.util.AppResult
import com.sopan.currency_conv.util.NetworkManager.isOnline
import com.sopan.currency_conv.util.TAG
import com.sopan.currency_conv.util.Utils.handleApiError
import com.sopan.currency_conv.util.Utils.handleSuccess
import com.sopan.currency_conv.util.noNetworkConnectivityError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.concurrent.TimeUnit

class DataRepositoryImpl(

) : DataRepository, KoinComponent {

    val accessKey = BuildConfig.ACCESS_KEY
    val api: CurrencyConvApi by inject()
    val dao: SupportedCurrenciesDao by inject()
    val currencyRateDao: CurrencyRateDao by inject()
    val prefUtility: SharedPref by inject()
    val context: Context by inject()

    override suspend fun getSupportedCurrencies(): AppResult<List<SupportedCurrency>?> {
        for (x in 0 until 3) {
            println(" Data manipulation prior to REST API request if required $x")
        }
        val localData = getSupportedCurrenciesFromCache()
        if (!localData.isEmpty()) {
            Logger.d(TAG, "getSupportedCurrencies(): Returns Local Cache Data")
            return AppResult.Success(localData)
        }

        return if (isOnline(context)) {
            val data = getSupportedCurrenciesFromApi()
            when (data) {
                is AppResult.Success -> {
                    withContext(Dispatchers.IO) {
                        dao.insert(data.successData)
                    }
                }
                else -> {}
            }
            return data


        } else {
            context.noNetworkConnectivityError()
        }
    }

    override suspend fun getSupportedCurrenciesFromApi(): AppResult<List<SupportedCurrency>?> {

        return try {
            val response = api.getSupportedCountries(accessKey)
            if (response.isSuccessful) {

                return when (val result = handleSuccess(response)) {
                    is AppResult.Success -> {
                        val data = getSupportedCurrenciesList(response.body()?.supportedCurrenciesMap)
                        AppResult.Success(data)
                    }
                    is AppResult.Error -> {
                        AppResult.Error(Exception(result.message))
                    }
                }

            } else {
                handleApiError(response)
            }
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }

    override suspend fun getCurrencyRate(): AppResult<List<CurrencyRateToDisplay>> {
        val localData = getCurrencyRateToDisplayFromCache()
        val isSyncRequired = isSyncRequired()
        if (!isSyncRequired && !localData.isNullOrEmpty()) {
            Logger.i(TAG, "getCurrencyRate(): Returns Local Cache Data")
            return AppResult.Success(localData)
        }
        Logger.d(TAG, "getCurrencyRate(): Returns Local Cache Data = ${localData.size}")

        if (isOnline(context)) {

            val apiData = getCurrencyRateFromApi()
            var returnData = listOf<CurrencyRateToDisplay>()
            when (apiData) {
                is AppResult.Success -> {
                    withContext(Dispatchers.IO) {

                        currencyRateDao.insert(apiData.successData)
                        prefUtility.saveLastSyncedTime(System.currentTimeMillis())
                        returnData = getCurrencyRateToDisplayFromCache()
                    }
                    AppResult.Success(returnData)
                }
                is AppResult.Error -> {
                    AppResult.Error(Exception(apiData.message))
                }
            }
            return AppResult.Success(returnData)

        } else {
            //check in db if the data exists
            //no network
            return context.noNetworkConnectivityError()
        }

    }

    override suspend fun getCurrencyRateFromApi(): AppResult<List<CurrencyRate>> {
        return try {
            val response = api.getCurrencyRate(accessKey)
            if (response.isSuccessful) {

                return when (val result = handleSuccess(response)) {
                    is AppResult.Success -> {
                        val data = getCurrencyRateList(response.body()?.rates)

                        AppResult.Success(data)
                    }
                    is AppResult.Error -> {
                        AppResult.Error(Exception(result.message))
                    }
                }

            } else {
                handleApiError(response)
            }
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }


    private suspend fun getSupportedCurrenciesFromCache(): List<SupportedCurrency> {
        return withContext(Dispatchers.IO) {
            dao.findAll()
        }
    }

    private suspend fun getCurrencyRateToDisplayFromCache(): List<CurrencyRateToDisplay> {
        return withContext(Dispatchers.IO) {
            currencyRateDao.getAllCurrencyRateWithCountryName()
        }
    }


    /**
     * Converts Supported Currencies Map to List
     */
    private fun getSupportedCurrenciesList(map: Map<String, String>?): List<SupportedCurrency>? {
        val list = ArrayList<SupportedCurrency>()
        if (!map.isNullOrEmpty()) {
            val keys = map.keys
            for (key in keys) {
                val rate = SupportedCurrency(key, map[key])
                list.add(rate)
            }
        }
        return list
    }

    /**
     * Converts Supported Currencies Map to List
     */
    private fun getCurrencyRateList(map: Map<String, Double>?): List<CurrencyRate> {
        val list = ArrayList<CurrencyRate>()
        if (!map.isNullOrEmpty()) {
            val keys = map.keys
            for (key in keys) {
                var k = key
                if (!key.endsWith("USD", true)) {
                    k = key.replace("USD", "", true)
                } else {
                    k = "USD"
                }
                val rate = CurrencyRate(k, map[key])
                list.add(rate)
            }
        }
        return list
    }

    /**
     * Returns true if sync required, otherwise will return false
     */
    private fun isSyncRequired(): Boolean {
        val currentTimestamp = System.currentTimeMillis()
        val savedTimestamp = getLastSyncedTime()
        val diff = TimeUnit.MINUTES.toMillis(30)
        val out = currentTimestamp - savedTimestamp > diff
        Logger.d(TAG, "isSyncRequired() : out=$out ct=$currentTimestamp st=$savedTimestamp diff=$diff")
        return out
    }

    /**
     * Return last synced time
     */
    private fun getLastSyncedTime(): Long {
        return prefUtility.getLastSyncedTime()
    }
}