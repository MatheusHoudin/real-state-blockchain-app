package com.example.realstateblockchainapp.features.wallet.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun WalletPage() {
    val tabs = listOf("Coins", "NFT")

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize()
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
                Text(text = "Coins")
            } else {
                Text(text = "NFT")
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