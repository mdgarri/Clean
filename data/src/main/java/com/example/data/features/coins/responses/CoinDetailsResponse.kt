package com.example.data.features.coins.responses


import com.example.core.common.ConvertibleToModel
import com.example.core.models.CoinDetails
import com.google.gson.annotations.SerializedName

data class CoinDetailsResponse(
    val contract: String?,
    @SerializedName("development_status")
    val developmentStatus: String?,
    @SerializedName("first_data_at")
    val firstDataAt: String?,
    @SerializedName("hardware_wallet")
    val hardwareWallet: Boolean?,
    @SerializedName("hash_algorithm")
    val hashAlgorithm: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("is_active")
    val isActive: Boolean?,
    @SerializedName("is_new")
    val isNew: Boolean?,
    @SerializedName("last_data_at")
    val lastDataAt: String?,
    val message: String?,
    val name: String?,
    @SerializedName("open_source")
    val openSource: Boolean?,
    @SerializedName("org_structure")
    val orgStructure: String?,
    val platform: String?,
    @SerializedName("proof_type")
    val proofType: String?,
    val rank: Int?,
    @SerializedName("started_at")
    val startedAt: String?,
    val symbol: String?,
    val type: String?,
) : ConvertibleToModel<CoinDetails> {
    override fun toModel() = CoinDetails(
        contract = contract ?: "",
        developmentStatus = developmentStatus ?: "",
        firstDataAt = firstDataAt ?: "",
        hardwareWallet = hardwareWallet ?: true,
        hashAlgorithm = hashAlgorithm ?: "",
        id = id ?: "",
        isActive = isActive ?: true,
        isNew = isNew ?: true,
        lastDataAt = lastDataAt ?: "",
        message = message ?: "",
        name = name ?: "",
    )
}