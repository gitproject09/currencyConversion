package com.sopan.currency_conv.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopan.currency_conv.model.CurrencyRateToDisplay
import com.sopan.currency_conv.model.SupportedCurrency
import com.sopan.currency_conv.repository.DataRepository
import com.sopan.currency_conv.util.AppResult
import com.sopan.currency_conv.util.SingleLiveEvent
import kotlinx.coroutines.launch


class DataViewModel(private val repository: DataRepository) : ViewModel() {

    val showLoading = ObservableBoolean()
    val countriesList = MutableLiveData<List<SupportedCurrency>>()
    val currencyRateList = MutableLiveData<List<CurrencyRateToDisplay>>()
    val showError = SingleLiveEvent<String?>()

    var mCurrentMultiplier = ObservableField<String>("1.0")
    var mCurrentCurrency = MutableLiveData<CurrencyRateToDisplay>()

    fun onSelectItem(pos: Int) {
        mCurrentCurrency.postValue(currencyRateList.value?.get(pos))
    }

    /**
     * Returns Supported countries list
     */
    fun getAllSupportedCurrencies() {
        showLoading.set(true)
        viewModelScope.launch {

            val result = repository.getSupportedCurrencies()

            showLoading.set(false)
            when (result) {
                is AppResult.Success -> {
                    //val data=result.successData
                    countriesList.value = result.successData!!
                    showError.value = null
                }
                is AppResult.Error -> showError.value = result.exception.message
            }
        }
    }

    /**
     * Returns currency rate list
     */

    fun getAllCurrencyRates() {
        showLoading.set(true)
        viewModelScope.launch {
            val result = repository.getCurrencyRate()

            showLoading.set(false)
            when (result) {
                is AppResult.Success -> {
                    //val data=result.successData
                    val data = result.successData
                    currencyRateList.value = data
                    showError.value = null
                }
                is AppResult.Error -> showError.value = result.exception.message
            }
        }
    }


}