package com.sopan.currency_conv.model

import com.google.gson.annotations.SerializedName

class CurrencyRateResponse : CurrencyConvResponse() {

    var source: String? = null

    /**
     * Map contains currency code and respective rate
     */
    @SerializedName("quotes")
    var rates: Map<String, Double>? = null
}