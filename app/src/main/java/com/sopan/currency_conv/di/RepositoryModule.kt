package com.sopan.currency_conv.di

import com.sopan.currency_conv.repository.DataRepository
import com.sopan.currency_conv.repository.DataRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    fun provideDataRepository(): DataRepository {
        return DataRepositoryImpl()
    }
    factory { provideDataRepository() }
}