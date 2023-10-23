package com.example.realstateblockchainapp.shared.api

import com.example.realstateblockchainapp.shared.api.models.NftModel
import com.example.realstateblockchainapp.shared.api.models.RealStateNft
import retrofit2.http.GET
import retrofit2.http.Header


interface NFTApi {
    @GET("realStateNft")
    suspend fun getRealStateNFT(@Header("privateKey") privateKey: String): RealStateNft

    @GET("nfts")
    suspend fun getNFTs(@Header("privateKey") privateKey: String): List<NftModel>

}