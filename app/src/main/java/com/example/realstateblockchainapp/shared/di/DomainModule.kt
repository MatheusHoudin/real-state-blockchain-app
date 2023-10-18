package com.example.realstateblockchainapp.shared.di

import com.example.realstateblockchainapp.features.home.domain.HomeUseCase
import com.example.realstateblockchainapp.features.home.mapper.HomeNftMapper
import org.koin.dsl.module

val domainModule = module {
    factory { HomeNftMapper() }
    factory { HomeUseCase(
        homeRepository = get(),
        homeNftMapper = get()
    ) }
}