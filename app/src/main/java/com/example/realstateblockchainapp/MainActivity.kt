package com.example.realstateblockchainapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.realstateblockchainapp.features.home.ui.HomePage
import com.example.realstateblockchainapp.features.login.ui.LoginPage
import com.example.realstateblockchainapp.shared.preferences.PRIVATE_WALLET_KEY
import com.example.realstateblockchainapp.shared.preferences.PreferencesRepository
import com.example.realstateblockchainapp.shared.preferences.USER_EMAIL_KEY
import com.example.realstateblockchainapp.shared.preferences.USER_NAME_KEY
import com.example.realstateblockchainapp.shared.utils.NavConstants.HOME_PAGE
import com.example.realstateblockchainapp.shared.utils.NavConstants.LOGIN_PAGE
import com.example.realstateblockchainapp.shared.web3.Authenticator
import com.example.realstateblockchainapp.ui.theme.RealStateBlockchainAppTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.concurrent.CompletableFuture

class MainActivity : ComponentActivity() {

    private lateinit var authenticator: Authenticator
    private val preferencesRepository: PreferencesRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authenticator = Authenticator(this)

        intent?.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        authenticator.setIntent(intent?.data)

        setContent {
            val navController = rememberNavController()
            LaunchedEffect("sessionKey") {
                val sessionResponse: CompletableFuture<Void> = authenticator.web3Auth.initialize()
                sessionResponse.whenComplete { _, error ->
                    try {
                        val privateKey = authenticator.web3Auth.getPrivkey()
                        if (error == null && privateKey.isNotEmpty()) {
                            lifecycleScope.launch {
                                preferencesRepository.putString(
                                    PRIVATE_WALLET_KEY,
                                    authenticator.web3Auth.getPrivkey()
                                )
                                preferencesRepository.putString(
                                    USER_EMAIL_KEY,
                                    authenticator.web3Auth.getUserInfo()?.email.orEmpty()
                                )
                                preferencesRepository.putString(
                                    USER_NAME_KEY,
                                    authenticator.web3Auth.getUserInfo()?.name.orEmpty()
                                )
                                navController.navigate(HOME_PAGE) {
                                    popUpTo(LOGIN_PAGE) { inclusive = true }
                                }
                            }
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Something went wrong: ${error.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } catch (e: Exception) {
                        Log.d("MainActivity_Web3Auth", e.message ?: "Something went wrong")
                    }
                }
            }
            RealStateBlockchainAppTheme {
                NavHost(navController = navController, startDestination = LOGIN_PAGE) {
                    composable(LOGIN_PAGE) { LoginPage { loginWithProvider() } }
                    composable(HOME_PAGE) { HomePage() }
                }
            }
        }
    }

    private fun loginWithProvider() = authenticator.login()

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        authenticator.setIntent(intent?.data)
    }
}
