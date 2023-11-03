package com.example.realstateblockchainapp.features.navigation.viewmodel

import com.example.realstateblockchainapp.features.navigation.model.CreateNftRequest
import com.example.realstateblockchainapp.shared.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavigationViewModel : BaseViewModel() {
    private val _navigationState: MutableStateFlow<NavigationDomain> =
        MutableStateFlow(NavigationDomain())
    val navigationState: StateFlow<NavigationDomain> = _navigationState.asStateFlow()

    fun createNft(request: CreateNftRequest) {

    }

    fun showCreateNftDialog() {
        _navigationState.value = _navigationState.value.copy(
            showCreateNftDialog = true
        )
    }

    fun hideCreateNftDialog() {
        _navigationState.value = _navigationState.value.copy(
            showCreateNftDialog = false
        )
    }
}

data class NavigationDomain(
    val showCreateNftDialog: Boolean = false
)