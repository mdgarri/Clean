package com.example.data.features.coins

import com.example.core.common.asCache
import com.example.core.common.toModel
import com.example.data.features.coins.entites.CoinEntity
import kotlinx.coroutines.flow.flow

class CoinsRepository(
    private val coinsAPI: CoinsAPI,
    private val coinsDataBase: CoinsDataBase
) {

    suspend fun getCoins(cached: Boolean = false) = flow {
        if (cached) {
            coinsDataBase.coinsDao().get()
                .takeIf {
                    it.isNotEmpty()
                }
                ?.asCache()
                ?.toModel()
                ?.let { cachedCoins ->
                    emit(cachedCoins)
                }
        }
        coinsAPI.getCoins()
            .toModel()
            .doOnSuccess {
                coinsDataBase.coinsDao().insert( it.map { CoinEntity(it) } )
            }.let {
                emit(it)
            }
    }

}