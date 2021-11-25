package com.example.clean.di

import com.example.data.features.coins.CoinsRepository
import com.example.domain.interactors.GetCoinsInteractor
import org.koin.dsl.module

val domainKoinModule = module {
    single<GetCoinsInteractor> { GetCoinsInteractor(get<CoinsRepository>()) }
}