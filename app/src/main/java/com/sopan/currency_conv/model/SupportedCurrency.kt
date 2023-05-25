package com.sopan.currency_conv.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * A model class which holds currency details like currency code and its rate
 * @param currencyCode : currency code
 * @param countryName : Name of the country
 *
 */
@Entity(tableName = "supported_currency")
@Parcelize
class SupportedCurrency(@PrimaryKey val currencyCode:String, val countryName:String?) : Parcelable {

    override fun toString(): String {
        return currencyCode.toString()
    }
}