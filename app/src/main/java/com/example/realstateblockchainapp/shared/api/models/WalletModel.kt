package com.example.realstateblockchainapp.shared.api.models

data class WalletModel(
    val tokens: List<CoinModel>,
    val nfts: List<NftModel>
)