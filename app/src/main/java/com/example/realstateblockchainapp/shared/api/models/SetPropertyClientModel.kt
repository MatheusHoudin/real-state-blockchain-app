package com.example.realstateblockchainapp.shared.api.models

data class SetPropertyClientModel(
    val client: String,
    val nftId: Int,
    val rentValue: String
)