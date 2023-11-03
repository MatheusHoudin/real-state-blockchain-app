package com.example.realstateblockchainapp.features.navigation.model

data class CreateNftRequest(
    val nftUri: String,
    val coinName: String,
    val coinSymbol: String,
    val initialSupply: String,
    val lockedAmount: String,
)