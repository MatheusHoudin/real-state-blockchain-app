package com.example.realstateblockchainapp.shared

import androidx.lifecycle.ViewModel
import com.example.realstateblockchainapp.shared.navigation.NavigationAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseViewModel : ViewModel() {
    private val _navigationAction: MutableStateFlow<NavigationAction> =
        MutableStateFlow(NavigationAction.None)
    val navigationAction: StateFlow<NavigationAction> = _navigationAction.asStateFlow()

    fun handleNavigation(action: NavigationAction) {
        _navigationAction.value = action
    }
}