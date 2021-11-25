package com.example.clean.di

import com.example.clean.presentation.coins.CoinsViewModel
import com.example.domain.interactors.GetCoinsInteractor
import org.koin.dsl.module

val viewModelsKoinModule = module {
    single<CoinsViewModel> { CoinsViewModel(get<GetCoinsInteractor>()) }

}