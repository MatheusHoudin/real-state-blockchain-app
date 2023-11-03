package com.example.realstateblockchainapp.shared.api

import com.example.realstateblockchainapp.shared.api.models.WalletModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface WalletApi {
    @GET("wallet/{address}")
    suspend fun getWalletData(
        @Header("privateKey") privateKey: String,
        @Path("address") address: String
    ): WalletModel
}