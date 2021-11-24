package com.example.data.features.coins

import com.example.core.common.NetResult
import com.example.data.features.coins.responses.CoinResponse
import retrofit2.http.GET

interface CoinsAPI {

    @GET("coins")
    fun getCoins() : NetResult<CoinResponse>

}