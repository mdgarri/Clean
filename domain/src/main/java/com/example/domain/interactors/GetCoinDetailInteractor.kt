package com.example.domain.interactors

import com.example.core.common.NetResult
import com.example.core.common.RequestType
import com.example.data.features.coins.CoinsRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class GetCoinDetailInteractor(
    private val coinsRepository: CoinsRepository
) {

    operator fun invoke(requestType: RequestType, coinId: String) = flow {
        emit(NetResult.Loading)
        coinsRepository.getCoinDetails(requestType, coinId).collect {
            emit(it)
        }
    }
}