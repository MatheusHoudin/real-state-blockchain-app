package com.example.realstateblockchainapp.features.splash.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.realstateblockchainapp.shared.preferences.PRIVATE_WALLET_KEY
import com.example.realstateblockchainapp.shared.preferences.PreferencesRepository
import com.example.realstateblockchainapp.shared.utils.NavConstants

@Composable
fun SplashPage(
    preferencesRepository: PreferencesRepository,
    navController: NavHostController
) {
    LaunchedEffect("splash") {
        val loggedPrivateKey = preferencesRepository.getString(PRIVATE_WALLET_KEY)

        val routeToOpen =
            if (loggedPrivateKey != null) NavConstants.NAVIGATION_PAGE else NavConstants.LOGIN_PAGE

        navController.navigate(routeToOpen) {
            popUpTo(NavConstants.SPLASH_PAGE) { inclusive = true }

        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    )
}