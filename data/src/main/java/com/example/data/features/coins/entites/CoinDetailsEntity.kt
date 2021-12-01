package com.example.data.features.coins.entites

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.common.ConvertibleToModel
import com.example.core.models.CoinDetails

@Entity
data class CoinDetailsEntity constructor(
    val contract: String,
    val developmentStatus: String,
    val firstDataAt: String,
    val hardwareWallet: Boolean,
    val hashAlgorithm: String,
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val isActive: Boolean,
    val isNew: Boolean,
    val lastDataAt: String,
    val message: String,
    val name: String,
) : ConvertibleToModel<CoinDetails> {

    override fun toModel() = CoinDetails(
        contract = contract,
        developmentStatus = developmentStatus,
        firstDataAt = firstDataAt,
        hardwareWallet = hardwareWallet,
        hashAlgorithm = hashAlgorithm,
        id = id,
        isActive = isActive,
        isNew = isNew,
        lastDataAt = lastDataAt,
        message = message,
        name = name,
    )


    constructor(coinDetails: CoinDetails) : this(
        contract = coinDetails.contract,
        developmentStatus = coinDetails.developmentStatus,
        firstDataAt = coinDetails.firstDataAt,
        hardwareWallet = coinDetails.hardwareWallet,
        hashAlgorithm = coinDetails.hashAlgorithm,
        id = coinDetails.id,
        isActive = coinDetails.isActive,
        isNew = coinDetails.isNew,
        lastDataAt = coinDetails.lastDataAt,
        message = coinDetails.message,
        name = coinDetails.name,
    )

}
