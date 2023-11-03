package com.example.realstateblockchainapp.shared.di

import com.example.realstateblockchainapp.features.home.domain.BuyCoinsUseCase
import com.example.realstateblockchainapp.features.home.domain.GetNftDetailsUseCase
import com.example.realstateblockchainapp.features.home.domain.HomeUseCase
import com.example.realstateblockchainapp.features.home.mapper.HomeNftMapper
import com.example.realstateblockchainapp.features.home.mapper.NftDetailsMapper
import com.example.realstateblockchainapp.features.wallet.domain.GetWalletUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { HomeNftMapper() }
    factory { NftDetailsMapper() }
    factory { HomeUseCase(
        nftRepository = get(),
        homeNftMapper = get(),
        preferencesRepository = get()
    ) }
    factory { GetNftDetailsUseCase(
        nftRepository = get(),
        coinRepository = get(),
        nftDetailsMapper = get()
    ) }
    factory { BuyCoinsUseCase(
        coinRepository = get(),
    ) }
    factory { GetWalletUseCase(
        repository = get(),
    ) }
}