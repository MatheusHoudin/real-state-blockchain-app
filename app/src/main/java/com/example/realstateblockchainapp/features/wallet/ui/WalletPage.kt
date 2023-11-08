package com.example.realstateblockchainapp.features.wallet.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.realstateblockchainapp.features.wallet.viewmodel.WalletViewModel
import com.example.realstateblockchainapp.features.wallet.viewmodel.tabs.NftTab
import com.example.realstateblockchainapp.features.wallet.viewmodel.tabs.TokensTab
import com.example.realstateblockchainapp.shared.components.BuyCoinsDialog
import com.example.realstateblockchainapp.shared.components.LoadingOrContent
import com.example.realstateblockchainapp.shared.components.NftDetailsBottomSheet
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun WalletPage() {
    val tabs = listOf("Coins", "NFT")

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val walletVm = koinViewModel<WalletViewModel>()
    val state = walletVm.walletState.collectAsState()

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
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    },
                    backgroundColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    tabs.forEachIndexed { index, value ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                            text = {
                                TabItem(tabValue = value, isSelected = pagerState.currentPage == index)
                            }
                        )
                    }
                }
                HorizontalPager(
                    count = tabs.size,
                    state = pagerState,
                ) {
                    if (pagerState.currentPage == 0) {
                        TokensTab(tokens = state.value.tokens)
                    } else {
                        NftTab(nfts = state.value.nfts) { nftId ->
                            walletVm.openNftDetails(nftId)
                        }
                    }
                }
                NftDetailsBottomSheet(
                    state.value.nftDetails,
                    walletVm::showBuyCoinDialog,
                    walletVm::setPropertyClient,
                    setPropertyClientHash = state.value.addClientPropertyHash,
                    isSetPropertyClientLoading = state.value.isAddingClientToProperty
                ) {
                    walletVm.onCloseNftDetails()
                }
                state.value.nftDetails?.coinDetails?.let { coinDetails ->
                    state.value.buyCoinState?.let { buyCoinState ->
                        BuyCoinsDialog(
                            coinDetails = coinDetails,
                            buyCoins = walletVm::buyCoins,
                            onChangeCoinsQuantity = walletVm::onChangeCoinsQuantity,
                            buyCoinState = buyCoinState
                        ) {
                            walletVm.hideBuyCoinDialog()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TabItem(tabValue: String, isSelected: Boolean) {
    Text(
        text = tabValue,
        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
}