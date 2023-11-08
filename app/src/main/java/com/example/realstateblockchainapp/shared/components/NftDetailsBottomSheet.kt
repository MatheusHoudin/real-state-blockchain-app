package com.example.realstateblockchainapp.shared.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.realstateblockchainapp.features.home.model.NftDetailsDomainModel
import com.example.realstateblockchainapp.shared.api.models.SetPropertyClientModel
import com.example.realstateblockchainapp.shared.utils.openUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NftDetailsBottomSheet(
    model: NftDetailsDomainModel?,
    openBuyCoinsDialog: () -> Unit,
    setPropertyClient: (SetPropertyClientModel) -> Unit,
    setPropertyClientHash: String? = null,
    isSetPropertyClientLoading: Boolean? = null,
    onDismiss: () -> Unit
) {
    model?.let {
        val sheetState = rememberModalBottomSheetState()

        LaunchedEffect("") {
            sheetState.expand()
        }
        if (sheetState.isVisible) {
            val context = LocalContext.current
            ModalBottomSheet(
                onDismissRequest = { onDismiss() },
                sheetState = sheetState,
                containerColor = MaterialTheme.colorScheme.onPrimary
            ) {
                // Sheet content
                LoadingOrContent(
                    isLoading = model.isLoading,
                    modifier = Modifier.fillMaxWidth(),
                    progressColor = MaterialTheme.colorScheme.primary
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        AsyncImage(
                            model = model.metadata?.image,
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .height(160.dp),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "#${model.nftId} - ${it.metadata?.name.orEmpty()}",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = it.metadata?.description.orEmpty(),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                openUrl(context, it.metadata?.externalLink.orEmpty())
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(
                                text = "View building",
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        model.propertyClient?.let { propertyClient ->

                            HorizontalDivider(
                                modifier = Modifier.fillMaxWidth(),
                                thickness = 2.dp,
                                color = MaterialTheme.colorScheme.primaryContainer
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "This property is already rented",
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.bodyLarge,
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = {
                                    openUrl(context, propertyClient.client)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text(
                                    text = "View property client",
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Contract price: ${propertyClient.value} ETH",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        } ?: kotlin.run {

                            if (setPropertyClientHash != null) {
                                val context = LocalContext.current
                                Button(
                                    onClick = {
                                        openUrl(context, setPropertyClientHash)
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
                                LoadingOrContent(
                                    isLoading = isSetPropertyClientLoading == true,
                                    progressColor = MaterialTheme.colorScheme.primary
                                ) {
                                    var clientAddress by remember { mutableStateOf("") }
                                    var rentValue by remember { mutableStateOf("") }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "You can rent this property",
                                        color = MaterialTheme.colorScheme.onBackground,
                                        style = MaterialTheme.typography.bodyLarge,
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    TextField(
                                        value = clientAddress,
                                        placeholder = {
                                            Text("Client wallet address")
                                        },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                        onValueChange = { value ->
                                            clientAddress = value
                                        },
                                        colors = TextFieldDefaults.colors(
                                            focusedContainerColor = Color.Transparent,
                                            unfocusedContainerColor = Color.Transparent,
                                            disabledContainerColor = Color.Transparent,
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    TextField(
                                        value = rentValue,
                                        placeholder = {
                                            Text("Rent value in ETH")
                                        },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        onValueChange = { value ->
                                            rentValue = value
                                        },
                                        colors = TextFieldDefaults.colors(
                                            focusedContainerColor = Color.Transparent,
                                            unfocusedContainerColor = Color.Transparent,
                                            disabledContainerColor = Color.Transparent,
                                        )
                                    )
                                    Button(
                                        onClick = {
                                            setPropertyClient(SetPropertyClientModel(
                                                nftId = model.nftId.toInt(),
                                                client = clientAddress,
                                                rentValue = rentValue
                                            ))
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.primary
                                        )
                                    ) {
                                        Text(
                                            text = "Rent property",
                                            color = MaterialTheme.colorScheme.onPrimary
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }


                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 2.dp,
                            color = MaterialTheme.colorScheme.primaryContainer
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Coin: ${model.coinDetails?.name} - ${model.coinDetails?.symbol}",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            Button(
                                onClick = {
                                    openUrl(context, model.coinAddress)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text(
                                    text = "View ${model.coinDetails?.symbol}",
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Button(
                                onClick = {
                                    openBuyCoinsDialog()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Text(
                                    text = "Buy coins",
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Available: ${model.coinDetails?.availableTokenAmount} ${model.coinDetails?.symbol}",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            Text(
                                text = "Supply: ${model.coinDetails?.totalSupply} ${model.coinDetails?.symbol}",
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Center
                            )
                            VerticalDivider(
                                modifier = Modifier.height(12.dp),
                                thickness = 2.dp,
                                color = MaterialTheme.colorScheme.primaryContainer
                            )
                            Text(
                                text = "Locked: ${model.coinDetails?.lockedAmount} ${model.coinDetails?.symbol}",
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Center
                            )
                        }

                    }
                }
            }
        }

    }
}