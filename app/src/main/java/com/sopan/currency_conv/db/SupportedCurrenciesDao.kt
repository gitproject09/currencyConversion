package com.sopan.currency_conv.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sopan.currency_conv.model.SupportedCurrency

@Dao
interface SupportedCurrenciesDao {

  /*  @Query("SELECT * FROM Countries")
    fun findAll(): List<CountriesData>*/

   /* @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(users: List<CountriesData>)*/

   @Query("SELECT * FROM supported_currency")
   fun findAll(): List<SupportedCurrency>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<SupportedCurrency>?)

}