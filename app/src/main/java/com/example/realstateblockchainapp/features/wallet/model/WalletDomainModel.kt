package com.example.realstateblockchainapp.features.wallet.model

import com.example.realstateblockchainapp.features.home.model.BuyCoinState
import com.example.realstateblockchainapp.features.home.model.NftDetailsDomainModel
import com.example.realstateblockchainapp.shared.api.models.CoinModel
import com.example.realstateblockchainapp.shared.api.models.NftModel

data class WalletDomainModel(
    val isLoading: Boolean = false,
    val nfts: List<NftModel> = emptyList(),
    val tokens: List<CoinModel> = emptyList(),
    val nftDetails: NftDetailsDomainModel? = null,
    val buyCoinState: BuyCoinState? = null
)