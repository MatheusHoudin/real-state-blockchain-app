package com.example.realstateblockchainapp.shared.di

import com.example.realstateblockchainapp.features.home.viewmodel.HomeViewModel
import com.example.realstateblockchainapp.features.login.viewmodel.LoginViewModel
import com.example.realstateblockchainapp.shared.preferences.PreferencesRepository
import com.example.realstateblockchainapp.shared.preferences.PreferencesRepositoryImpl
import com.example.realstateblockchainapp.shared.web3.Authenticator
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { Authenticator(androidContext()) }
    single<PreferencesRepository> { PreferencesRepositoryImpl(androidContext()) }
    viewModel { LoginViewModel(preferencesRepository = get()) }
    viewModel { HomeViewModel(homeUseCase = get(), getNftDetailsUseCase = get()) }
}