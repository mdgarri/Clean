package com.example.data.features.coins

import com.example.core.common.toModel
import com.example.core.models.Coin
import kotlinx.coroutines.flow.flow

class CoinsRepository(
    private val coinsAPI: CoinsAPI
) {

    suspend fun getCoins(cached: Boolean = false) = flow {
        //emit(Coin("",true,true, "", 1 ,"", ""))
        emit(coinsAPI.getCoins().toModel())
    }

}