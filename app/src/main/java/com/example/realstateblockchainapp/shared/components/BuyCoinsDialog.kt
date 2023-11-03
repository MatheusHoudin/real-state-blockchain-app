package com.example.realstateblockchainapp.shared.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.realstateblockchainapp.features.home.model.BuyCoinState
import com.example.realstateblockchainapp.features.home.model.CoinDetailsDomainModel
import com.example.realstateblockchainapp.shared.utils.openUrl

@Composable
fun BuyCoinsDialog(
    coinDetails: CoinDetailsDomainModel,
    buyCoins: (String) -> Unit,
    onChangeCoinsQuantity: (String) -> Unit,
    buyCoinState: BuyCoinState,
    onDismiss: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        var coinsQuantityText by remember { mutableStateOf("") }
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(8.dp)
        ) {
            LoadingOrContent(
                isLoading = buyCoinState.buyCoinLoading,
                modifier = Modifier.fillMaxWidth(),
                progressColor = MaterialTheme.colorScheme.primary
            ) {
                if (buyCoinState.resultMessage.isNotEmpty()) {
                    val context = LocalContext.current
                    Button(
                        onClick = {
                            openUrl(context, buyCoinState.resultMessage)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = "View transaction",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Available: ${coinDetails.availableTokenAmount} ${coinDetails.symbol}",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 6.dp),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = coinsQuantityText,
                            placeholder = {
                                Text("How many coins do you want?")
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            onValueChange = { value ->
                                coinsQuantityText = value
                                onChangeCoinsQuantity(value)
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Buy cost: ${buyCoinState.valueInEth}",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                buyCoins(coinsQuantityText)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(
                                text = "Buy Coins",
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}