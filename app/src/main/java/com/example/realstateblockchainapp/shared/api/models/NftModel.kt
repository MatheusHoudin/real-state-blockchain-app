package com.example.realstateblockchainapp.shared.api.models

import com.google.gson.annotations.SerializedName

data class NftModel(
    val tokenId: String,
    val metadata: NftMetadata? = null
)

data class NftMetadata(
    val name: String,
    val description: String,
    val image: String,
    @SerializedName("external_link")
    val externalLink: String,
)
