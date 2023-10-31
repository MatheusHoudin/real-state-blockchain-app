package com.example.realstateblockchainapp.shared.repository

import com.example.realstateblockchainapp.shared.api.CoinApi
import com.example.realstateblockchainapp.shared.api.models.BuyCoinsRequest
import com.example.realstateblockchainapp.shared.api.models.CoinDetails
import com.example.realstateblockchainapp.shared.api.models.GenericTransactionResponse
import com.example.realstateblockchainapp.shared.preferences.PRIVATE_WALLET_KEY
import com.example.realstateblockchainapp.shared.preferences.PUBLIC_WALLET_KEY
import com.example.realstateblockchainapp.shared.preferences.PreferencesRepository

class CoinRepository(
    private val coinApi: CoinApi,
    private val preferencesRepository: PreferencesRepository
) {

    suspend fun fetchCoinDetails(nftId: String): CoinDetails =
        coinApi.getCoinDetails(
            privateKey = preferencesRepository.getString(PRIVATE_WALLET_KEY).orEmpty(),
            nftId = nftId
        )

    suspend fun buyCoins(request: BuyCoinsRequest): GenericTransactionResponse = coinApi.buyCoins(
        privateKey = preferencesRepository.getString(PRIVATE_WALLET_KEY).orEmpty(),
        request = request.copy(
            buyerAddress = preferencesRepository.getString(PUBLIC_WALLET_KEY).orEmpty()
        )
    )
}