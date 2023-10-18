package com.example.realstateblockchainapp.shared.navigation

import androidx.navigation.NavHostController
import com.example.realstateblockchainapp.shared.utils.NavConstants

sealed class NavigationAction(
    val route: String
) {
    object None : NavigationAction("")
    object NavigateToHomePage : NavigationAction(NavConstants.HOME_PAGE)
}

fun NavHostController.navigate(action: NavigationAction) {
    if (action == NavigationAction.None) return
    navigate(action.route)
}