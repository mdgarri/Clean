package com.example.data.features.coins

import com.example.core.common.*
import com.example.core.models.Coin
import com.example.core.models.CoinDetails
import com.example.data.features.coins.entites.CoinDetailsEntity
import com.example.data.features.coins.entites.CoinEntity
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.coroutineContext

class CoinsRepository(
    private val coinsAPI: CoinsAPI,
    private val coinsDataBase: CoinsDataBase
) {

    suspend fun getCoins(requestType: RequestType) = flow<NetResult<List<Coin>>> {
        requestTypeHandler(requestType,
            cacheRequest = {
                coinsDataBase.coinsDao().get()
                    .takeIf {
                        it.isNotEmpty()
                    }
                    ?.asCache()
                    ?.toModel()
                    ?.let { cachedCoins ->
                        emit(cachedCoins)
                    }
            },
            apiRequest = {
                coinsAPI.getCoins()
                    .toModel()
                    .doOnSuccess(coroutineContext) {
                        coinsDataBase.coinsDao().insert(it.map { CoinEntity(it) })
                    }.let { coinFromApi ->
                        emit(coinFromApi)
                    }
            }
        )
    }

    suspend fun getCoinDetails(requestType: RequestType, coinId: String) = flow<NetResult<CoinDetails>> {
        requestTypeHandler(requestType,
            cacheRequest = {
                coinsDataBase.coinDetailsDao().get(coinId)
                    ?.asCache()
                    ?.toModel()
                    ?.let { cachedCoinDetails ->
                        emit(cachedCoinDetails.toModel())
                    }
            },
            apiRequest = {
                coinsAPI.getCoinDetails(coinId)
                    .toModel()
                    .doOnSuccess(coroutineContext) {
                        coinsDataBase.coinDetailsDao().insert( CoinDetailsEntity(it) )
                    }.let { coinDetailsFromApi ->
                        emit(coinDetailsFromApi)
                    }
            }
        )
    }

}