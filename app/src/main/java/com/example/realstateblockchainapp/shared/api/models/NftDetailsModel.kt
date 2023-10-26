package com.example.realstateblockchainapp.shared.api.models

data class NftDetailsModel(
    val nftId: String,
    val coinAddress: String,
    val propertyClient: PropertyClient,
    val nftData: NftMetadata?
)

data class PropertyClient(
    val client: String,
    val value: String
)