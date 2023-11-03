package com.example.realstateblockchainapp.shared.di

import com.example.realstateblockchainapp.shared.repository.CoinRepository
import com.example.realstateblockchainapp.shared.repository.NftRepository
import com.example.realstateblockchainapp.shared.repository.WalletRepository
import org.koin.dsl.module

val dataModule = module {
    factory {
        NftRepository(
            nftApi = get(),
            preferencesRepository = get()
        )
    }
    factory {
        CoinRepository(
            coinApi = get(),
            preferencesRepository = get()
        )
    }
    factory {
        WalletRepository(
            walletApi = get(),
            preferencesRepository = get()
        )
    }
}