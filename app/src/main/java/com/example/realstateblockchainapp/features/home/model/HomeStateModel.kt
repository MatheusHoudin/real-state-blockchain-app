package com.example.realstateblockchainapp.features.home.model

data class HomeStateModel(
    val isLoading: Boolean = false,
    val contractName: String = "",
    val contractSymbol: String = "",
    val nftPrice: String = "",
    val contractAddress: String = "",
    val userAddress: String = "",
)