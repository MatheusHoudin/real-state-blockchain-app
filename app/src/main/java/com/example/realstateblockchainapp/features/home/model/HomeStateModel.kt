package com.example.realstateblockchainapp.features.home.model

import com.example.realstateblockchainapp.shared.api.models.NftModel

data class HomeStateModel(
    val isLoading: Boolean = false,
    val contractName: String = "",
    val contractSymbol: String = "",
    val nftPrice: String = "",
    val userEthBalance: String = "",
    val contractAddress: String = "",
    val userAddress: String = "",
    val nfts: List<NftModel> = emptyList(),
    val nftDetails: NftDetailsDomainModel? = null,
    val showBuyCoinDialog: Boolean = false
)