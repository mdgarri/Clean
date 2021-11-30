package com.example.data.features.coins.entites

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.common.ConvertibleToModel
import com.example.core.models.Coin

@Entity
data class CoinEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val isActive: Boolean,
    val isNew: Boolean,
    val name: String,
    val rank: Int,
    val symbol: String,
    val type: String
) : ConvertibleToModel<Coin> {

    override fun toModel() = Coin(
        id = id,
        isActive = isActive,
        isNew = isNew,
        name = name,
        rank = rank,
        symbol = symbol,
        type = type,
    )

    constructor(coin: Coin): this(
        id = coin.id,
        isActive = coin.isActive,
        isNew = coin.isNew,
        name = coin.name,
        rank = coin.rank,
        symbol = coin.symbol,
        type = coin.type
    )

}
