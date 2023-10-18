package com.example.realstateblockchainapp.shared.di

import com.example.realstateblockchainapp.features.home.repository.HomeRepository
import org.koin.dsl.module

val dataModule = module {
    factory { HomeRepository(
        nftApi = get(),
        preferencesRepository = get()
    ) }
}