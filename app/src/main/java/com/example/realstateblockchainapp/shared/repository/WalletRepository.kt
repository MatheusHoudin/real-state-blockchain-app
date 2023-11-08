package com.example.realstateblockchainapp.shared.repository

import com.example.realstateblockchainapp.shared.api.WalletApi
import com.example.realstateblockchainapp.shared.api.models.GenericTransactionResponse
import com.example.realstateblockchainapp.shared.api.models.SetPropertyClientModel
import com.example.realstateblockchainapp.shared.api.models.WalletModel
import com.example.realstateblockchainapp.shared.preferences.PRIVATE_WALLET_KEY
import com.example.realstateblockchainapp.shared.preferences.PUBLIC_WALLET_KEY
import com.example.realstateblockchainapp.shared.preferences.PreferencesRepository

class WalletRepository(
    private val walletApi: WalletApi,
    private val preferencesRepository: PreferencesRepository
) {
    suspend fun getWalletData(): WalletModel = walletApi.getWalletData(
        privateKey = preferencesRepository.getString(PRIVATE_WALLET_KEY).orEmpty(),
        address = preferencesRepository.getString(PUBLIC_WALLET_KEY).orEmpty()
    )

    suspend fun setPropertyClient(model: SetPropertyClientModel): GenericTransactionResponse =
        walletApi.setPropertyClient(
            privateKey = preferencesRepository.getString(PRIVATE_WALLET_KEY).orEmpty(),
            model = model
        )
}