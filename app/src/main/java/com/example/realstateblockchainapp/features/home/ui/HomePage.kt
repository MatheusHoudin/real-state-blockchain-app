package com.example.realstateblockchainapp.features.home.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.realstateblockchainapp.features.home.model.BuyCoinState
import com.example.realstateblockchainapp.features.home.model.CoinDetailsDomainModel
import com.example.realstateblockchainapp.features.home.model.NftDetailsDomainModel
import com.example.realstateblockchainapp.features.home.viewmodel.HomeViewModel
import com.example.realstateblockchainapp.shared.api.models.NftModel
import com.example.realstateblockchainapp.shared.components.LoadingOrContent
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomePage() {

    val homeVm = koinViewModel<HomeViewModel>()
    val state = homeVm.homeState.collectAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.value.isLoading,
        onRefresh = homeVm::fetchNftData
    )

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
            Column(
                modifier = Modifier.pullRefresh(pullRefreshState)
            ) {
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
                    Text(
                        text = "My ETH: ${state.value.userEthBalance} ETH",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.padding(vertical = 4.dp))
                    Text(
                        text = "NFT Price: ${state.value.nftPrice} ETH",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                PullRefreshIndicator(
                    refreshing = state.value.isLoading,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
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
private fun NftCard(nft: NftModel, onClick: (String) -> Unit) {
    ElevatedCard(
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = Modifier.clickable { onClick(nft.tokenId) }
    ) {
        nft.metadata?.let {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = it.image,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .height(80.dp),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 4.dp,
                            horizontal = 6.dp
                        ),
                ) {
                    Text(
                        text = "#${nft.tokenId} - ${it.name}",
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.padding(vertical = 2.dp))
                    Text(
                        text = it.description,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NftDetailsBottomSheet(
    model: NftDetailsDomainModel?,
    openBuyCoinsDialog: () -> Unit,
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

private fun openUrl(context: Context, url: String) {
    val urlIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(url)
    )
    context.startActivity(urlIntent)
}