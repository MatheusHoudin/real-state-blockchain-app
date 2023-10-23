package com.example.realstateblockchainapp.features.home.repository

import com.example.realstateblockchainapp.shared.api.NFTApi
import com.example.realstateblockchainapp.shared.api.models.NftModel
import com.example.realstateblockchainapp.shared.api.models.RealStateNft
import com.example.realstateblockchainapp.shared.preferences.PRIVATE_WALLET_KEY
import com.example.realstateblockchainapp.shared.preferences.PreferencesRepository

class HomeRepository(
    private val nftApi: NFTApi,
    private val preferencesRepository: PreferencesRepository
) {

    suspend fun fetchRealStateNft(): RealStateNft =
        nftApi.getRealStateNFT(preferencesRepository.getString(PRIVATE_WALLET_KEY).orEmpty())

    suspend fun fetchAllNfts(): List<NftModel> =
        nftApi.getNFTs(preferencesRepository.getString(PRIVATE_WALLET_KEY).orEmpty())
}