package com.example.realstateblockchainapp.features.navigation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.realstateblockchainapp.features.home.ui.HomePage
import com.example.realstateblockchainapp.features.wallet.ui.WalletPage
import com.example.realstateblockchainapp.shared.utils.NavConstants

@Composable
fun NavigationPage() {

    var navigationSelectedItem by remember {
        mutableIntStateOf(0)
    }
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.onPrimary
            ) {
                BottomNavigationItem().bottomNavigationItems()
                    .forEachIndexed { index, navigationItem ->
                        NavigationBarItem(
                            selected = index == navigationSelectedItem,

                            label = {
                                Text(navigationItem.label)
                            },
                            icon = {
                                Icon(
                                    navigationItem.icon,
                                    contentDescription = navigationItem.label
                                )
                            },
                            onClick = {
                                navigationSelectedItem = index
                                navController.navigate(navigationItem.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = NavConstants.HOME_PAGE,
            modifier = Modifier.padding(it)
        ) {
            composable(NavConstants.HOME_PAGE) {
                HomePage()
            }
            composable(NavConstants.WALLET_PAGE) {
                WalletPage()
            }
        }
    }
}