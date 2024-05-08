package com.example.realstateblockchainapp.shared.api.models

data class CoinModel(
    val contractAddress: String,
    val rawBalance: String,
    val balance: String,
    val decimals: Int,
    val name: String,
    val symbol: String,
)
