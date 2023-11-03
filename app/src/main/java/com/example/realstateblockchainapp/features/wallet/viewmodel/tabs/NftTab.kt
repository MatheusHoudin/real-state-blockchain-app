package com.example.realstateblockchainapp.features.wallet.viewmodel.tabs

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.realstateblockchainapp.shared.api.models.NftModel
import com.example.realstateblockchainapp.shared.components.NftCard

@Composable
fun NftTab(nfts: List<NftModel>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 18.dp)
    ) {
        for (nft in nfts) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                NftCard(nft) {

                }
            }
        }
    }
}