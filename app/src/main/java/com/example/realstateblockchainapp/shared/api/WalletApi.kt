package com.example.realstateblockchainapp.shared.api

import com.example.realstateblockchainapp.shared.api.models.GenericTransactionResponse
import com.example.realstateblockchainapp.shared.api.models.SetPropertyClientModel
import com.example.realstateblockchainapp.shared.api.models.WalletModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface WalletApi {
    @GET("wallet/{address}")
    suspend fun getWalletData(
        @Header("privateKey") privateKey: String,
        @Path("address") address: String
    ): WalletModel

    @POST("setPropertyClient")
    suspend fun setPropertyClient(
        @Header("privateKey") privateKey: String,
        @Body model: SetPropertyClientModel
    ): GenericTransactionResponse
}