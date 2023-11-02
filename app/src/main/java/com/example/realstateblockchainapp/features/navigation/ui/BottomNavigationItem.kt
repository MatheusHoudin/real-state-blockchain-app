package com.example.realstateblockchainapp.features.navigation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.realstateblockchainapp.shared.utils.NavConstants

data class BottomNavigationItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = ""
) {

    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Home",
                icon = Icons.Filled.Home,
                route = NavConstants.HOME_PAGE
            ),
            BottomNavigationItem(
                label = "Wallet",
                icon = Icons.Filled.AccountCircle,
                route = NavConstants.WALLET_PAGE
            )
        )
    }
}