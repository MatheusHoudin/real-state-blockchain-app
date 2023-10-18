package com.example.realstateblockchainapp.features.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.realstateblockchainapp.features.home.repository.HomeRepository
import com.example.realstateblockchainapp.shared.BaseViewModel
import com.example.realstateblockchainapp.shared.navigation.NavigationAction
import com.example.realstateblockchainapp.shared.preferences.PRIVATE_WALLET_KEY
import com.example.realstateblockchainapp.shared.preferences.PreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeRepository: HomeRepository
) : BaseViewModel() {

    private val _homeState: MutableStateFlow<String> =
        MutableStateFlow("")
    val homeState: StateFlow<String> = _homeState.asStateFlow()

    init {
        fetchNftData()
    }

    private fun fetchNftData() {
        viewModelScope.launch {
            val realStateNft = homeRepository.fetchRealStateNft()
            _homeState.value = realStateNft.contractName
        }
    }
}