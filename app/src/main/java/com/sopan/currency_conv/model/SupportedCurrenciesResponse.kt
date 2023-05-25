package com.sopan.currency_conv.model

import com.google.gson.annotations.SerializedName

class SupportedCurrenciesResponse : CurrencyConvResponse() {
    /**
     * Map contains currency code and respective country name
     */
    @SerializedName("currencies")
    var supportedCurrenciesMap: Map<String, String>? = null
}