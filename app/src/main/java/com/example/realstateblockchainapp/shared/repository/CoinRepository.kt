package com.example.realstateblockchainapp.shared.repository

import com.example.realstateblockchainapp.shared.api.CoinApi
import com.example.realstateblockchainapp.shared.api.models.CoinDetails
import com.example.realstateblockchainapp.shared.preferences.PRIVATE_WALLET_KEY
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
}