package com.example.realstateblockchainapp.shared.web3

import android.content.Context
import android.net.Uri
import com.example.realstateblockchainapp.R
import com.web3auth.core.Web3Auth
import com.web3auth.core.types.LoginParams
import com.web3auth.core.types.Network
import com.web3auth.core.types.Provider
import com.web3auth.core.types.Web3AuthOptions
import com.web3auth.core.types.Web3AuthResponse
import com.web3auth.core.types.WhiteLabelData
import java.util.concurrent.CompletableFuture

class Authenticator(private val context: Context) {
    val web3Auth = Web3Auth(
        Web3AuthOptions(
            context = context,
            clientId = context.getString(R.string.web3auth_project_id),
            network = Network.TESTNET,
            redirectUrl = Uri.parse("com.example.realstateblockchainapp://auth"),
            whiteLabel = WhiteLabelData(
                "Web3Auth Android Example", null, null, "en", true,
                hashMapOf(
                    "primary" to "#229954"
                )
            ),
        )
    )

    fun setIntent(uri: Uri?) {
        web3Auth.setResultUrl(uri)
    }

    fun checkIfThereIsLoggedUser() {
        val sessionResponse: CompletableFuture<Void> = web3Auth.initialize()
    }

    fun login(): CompletableFuture<Web3AuthResponse> = web3Auth.login(LoginParams(Provider.GOOGLE))
}