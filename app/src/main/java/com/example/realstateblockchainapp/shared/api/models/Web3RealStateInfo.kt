package com.example.realstateblockchainapp.shared.api.models

data class RealStateNft(
    val contractName: String,
    val contractSymbol: String,
    val nftPrice: String,
    val contractAddress: String,
    val userEthBalance: String,
    val user: BlockchainUser
)

data class BlockchainUser(
    val address: String,
    val privateKey: String
)