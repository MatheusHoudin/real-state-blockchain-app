package com.example.realstateblockchainapp.shared.api.models

data class RealStateNFTModel(
    val web3RealStateNft: RealStateNft,
    val nfts: List<NftModel>
)