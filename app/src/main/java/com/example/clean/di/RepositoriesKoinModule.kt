package com.example.clean.di

import com.example.data.features.coins.CoinsAPI
import com.example.data.features.coins.CoinsRepository
import org.koin.dsl.module

val repositoriesKoinModule = module {
    single<CoinsRepository> { CoinsRepository(get<CoinsAPI>()) }

}