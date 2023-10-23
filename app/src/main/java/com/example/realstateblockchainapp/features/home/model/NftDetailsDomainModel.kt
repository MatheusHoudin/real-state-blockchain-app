package com.example.realstateblockchainapp.features.home.model

import com.example.realstateblockchainapp.shared.api.models.NftMetadata
import com.example.realstateblockchainapp.shared.api.models.PropertyClient

data class NftDetailsDomainModel(
    val isLoading: Boolean = false,
    val coinAddress: String = "",
    val propertyClient: PropertyClient? = null,
    val metadata: NftMetadata? = null,
    val coinDetails: CoinDetailsDomainModel? = null
)

data class CoinDetailsDomainModel(
    val address: String,
    val name: String,
    val symbol: String,
    val totalSupply: String,
    val lockedAmount: String,
    val availableTokenAmount: String,
    val totalRentIncomeReceived: String,
)

