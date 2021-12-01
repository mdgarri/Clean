package com.example.clean.di

import com.example.clean.presentation.coin_details.CoinDetailsViewModel
import com.example.clean.presentation.coins.CoinsViewModel
import com.example.domain.interactors.GetCoinDetailInteractor
import com.example.domain.interactors.GetCoinsInteractor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsKoinModule = module {

    viewModel { CoinsViewModel(get<GetCoinsInteractor>()) }
    viewModel {  params -> CoinDetailsViewModel( params.get(), get<GetCoinDetailInteractor>()) }

}