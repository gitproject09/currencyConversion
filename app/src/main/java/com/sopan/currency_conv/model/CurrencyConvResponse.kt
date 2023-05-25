package com.sopan.currency_conv.model

open class CurrencyConvResponse {
    var success: Boolean = false
    var error: Error? = null

    class Error {
        val code: Int = 0
        val info: String? = null
    }
}