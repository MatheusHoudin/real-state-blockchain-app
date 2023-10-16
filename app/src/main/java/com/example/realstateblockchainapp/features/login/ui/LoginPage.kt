package com.example.realstateblockchainapp.features.login.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(
    loginWithProvider: () -> Unit
) {
    var privateKeyText by remember { mutableStateOf("") }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                loginWithProvider()
            }) {
                Text(text = "Authenticate with Google")
            }
            Spacer(modifier = Modifier.height(22.dp))
            Divider(
                modifier = Modifier.padding(horizontal = 80.dp)
            )
            Spacer(modifier = Modifier.height(22.dp))
            Text(
                text = "Or use your Web3 account directly",
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(14.dp))
            PrivateKeyTextField(privateKeyText) { privateKey ->
                privateKeyText = privateKey
            }
            Button(onClick = {
                loginWithProvider()
            }) {
                Text(text = "Authenticate with private key")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PrivateKeyTextField(
    textValue: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = textValue,
        placeholder = {
            Text("Add your private key here")
        },
        onValueChange = { onValueChange(it) }
    )
}

@Preview
@Composable
fun LoginPagePreview() {
    LoginPage {

    }
}