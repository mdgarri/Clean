package com.example.domain.interactors

import com.example.core.common.NetResult
import com.example.data.features.coins.CoinsRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class GetCoinsInteractor(
    private val coinsRepository: CoinsRepository
){

    suspend operator fun invoke(cached: Boolean = false) = flow {
        emit(NetResult.Loading)
        coinsRepository.getCoins(cached)
            .collect {
                emit(it)
            }
    }


}


