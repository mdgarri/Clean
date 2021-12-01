package com.example.data.features.coins

import com.example.core.common.NetResult
import com.example.data.features.coins.responses.CoinDetailsResponse
import com.example.data.features.coins.responses.CoinResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinsAPI {

    @GET("coins")
    suspend fun getCoins() : NetResult<List<CoinResponse>>

    @GET("coins/{coin_id}")
    suspend fun getCoinDetails(@Path("coin_id") coindId: String) : NetResult<CoinDetailsResponse>

}