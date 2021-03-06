package com.example.domain.interactors

import com.example.core.common.NetResult
import com.example.core.common.RequestType
import com.example.data.features.coins.CoinsRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow

class GetCoinsInteractor(
    private val coinsRepository: CoinsRepository
){

    suspend operator fun invoke(requestType: RequestType) = flow {
        emit(NetResult.Loading)
        coinsRepository.getCoins(requestType)
            .collect {
                emit(it)
            }
    }


}


