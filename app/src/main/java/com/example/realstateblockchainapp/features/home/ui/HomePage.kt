package com.example.realstateblockchainapp.features.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.realstateblockchainapp.features.home.model.BuyCoinState
import com.example.realstateblockchainapp.features.home.model.CoinDetailsDomainModel
import com.example.realstateblockchainapp.features.home.viewmodel.HomeViewModel
import com.example.realstateblockchainapp.shared.components.LoadingOrContent
import com.example.realstateblockchainapp.shared.components.NftCard
import com.example.realstateblockchainapp.shared.components.NftDetailsBottomSheet
import com.example.realstateblockchainapp.shared.utils.openUrl
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomePage() {

    val homeVm = koinViewModel<HomeViewModel>()
    val state = homeVm.homeState.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.primary,
    ) {
        LoadingOrContent(
            isLoading = state.value.isLoading,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
        ) {
            Column {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                bottomStart = 46.dp,
                                bottomEnd = 46.dp
                            )
                        )
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${state.value.contractName} - ${state.value.contractSymbol}",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Spacer(modifier = Modifier.padding(vertical = 4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "My ETH: ${state.value.userEthBalance} ETH",
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(
                            onClick = {
                                homeVm.fetchNftData()
                            },
                        ) {
                            Icon(
                                Icons.Default.Refresh,
                                "",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.onPrimary)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(vertical = 4.dp))
                    Text(
                        text = "NFT Price: ${state.value.nftPrice} ETH",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    val context = LocalContext.current
                    Button(
                        onClick = {
                            openUrl(context, state.value.userAddress)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(
                            text = "View my account",
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Button(
                        onClick = {
                            openUrl(context, state.value.contractAddress)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(
                            text = "View RST contract",
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(vertical = 16.dp))
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(
                        horizontal = 16.dp
                    )
                ) {
                    item {
                        Text(
                            text = "Find the best investment for you",
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.padding(vertical = 8.dp))
                    }
                    for (nft in state.value.nfts) {
                        if (nft.metadata != null) {
                            item(key = nft.tokenId) {
                                NftCard(nft = nft) { nftId ->
                                    homeVm.openNftDetails(nftId)
                                }
                            }
                            item {
                                Spacer(modifier = Modifier.padding(vertical = 12.dp))
                            }
                        }
                    }
                }
                NftDetailsBottomSheet(state.value.nftDetails, homeVm::showBuyCoinDialog) {
                    homeVm.onCloseNftDetails()
                }
                state.value.nftDetails?.coinDetails?.let { coinDetails ->
                    state.value.buyCoinState?.let { buyCoinState ->
                        BuyCoinsDialog(
                            coinDetails = coinDetails,
                            buyCoins = homeVm::buyCoins,
                            onChangeCoinsQuantity = homeVm::onChangeCoinsQuantity,
                            buyCoinState = buyCoinState
                        ) {
                            homeVm.hideBuyCoinDialog()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BuyCoinsDialog(
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
