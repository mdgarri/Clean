package com.example.data.features.coins.responses

import com.example.core.common.JsonResponse
import com.example.core.models.Coin
import com.google.gson.annotations.SerializedName

data class CoinResponse(
    val id: String,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("is_new")
    val isNew: Boolean,
    val name: String,
    val rank: Int,
    val symbol: String,
    val type: String
) : JsonResponse<Coin> {

    override fun toModel() = Coin(
        id = id,
        isActive = isActive,
        isNew = isNew,
        name = name,
        rank = rank,
        symbol = symbol,
        type = type
    )

}