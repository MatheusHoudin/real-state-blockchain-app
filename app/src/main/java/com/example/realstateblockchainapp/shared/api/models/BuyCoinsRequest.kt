package com.example.realstateblockchainapp.shared.api.models

data class BuyCoinsRequest(
    val nftId: String,
    val buyerAddress: String = "",
    val ethValue: String
)