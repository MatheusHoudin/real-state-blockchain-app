package com.example.realstateblockchainapp.shared.api.models

data class CoinDetails(
    val address: String,
    val name: String,
    val symbol: String,
    val totalSupply: String,
    val lockedAmount: String,
    val availableTokenAmount: String,
    val totalRentIncomeReceived: String,
)