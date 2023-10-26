package com.example.realstateblockchainapp.shared.di

import com.example.realstateblockchainapp.features.home.domain.GetNftDetailsUseCase
import com.example.realstateblockchainapp.features.home.domain.HomeUseCase
import com.example.realstateblockchainapp.features.home.mapper.HomeNftMapper
import com.example.realstateblockchainapp.features.home.mapper.NftDetailsMapper
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
}