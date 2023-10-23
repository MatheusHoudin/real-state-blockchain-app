package com.example.realstateblockchainapp.shared.api

import com.example.realstateblockchainapp.shared.api.models.CoinDetails
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CoinApi {

    @GET("nftCoin/{id}")
    suspend fun getCoinDetails(
        @Header("privateKey") privateKey: String,
        @Path("id") nftId: String
    ): CoinDetails
}