package com.example.realstateblockchainapp.shared.api.models

data class NftDetailsModel(
    val coinAddress: String,
    val propertyClient: PropertyClient,
    val metadata: NftMetadata?
)

data class PropertyClient(
    val client: String,
    val value: String
)