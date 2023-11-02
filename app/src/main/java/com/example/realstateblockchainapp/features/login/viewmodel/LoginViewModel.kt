package com.example.realstateblockchainapp.features.login.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.realstateblockchainapp.shared.BaseViewModel
import com.example.realstateblockchainapp.shared.navigation.NavigationAction
import com.example.realstateblockchainapp.shared.preferences.PRIVATE_WALLET_KEY
import com.example.realstateblockchainapp.shared.preferences.PreferencesRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val preferencesRepository: PreferencesRepository
) : BaseViewModel() {

    fun loginWithPrivateKey(privateKey: String) {
        viewModelScope.launch {
            preferencesRepository.putString(PRIVATE_WALLET_KEY, privateKey)
            handleNavigation(NavigationAction.NavigateToNavigationPage)
        }
    }
}