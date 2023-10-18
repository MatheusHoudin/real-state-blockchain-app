package com.example.realstateblockchainapp.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FullLoadingOrContent(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    if (isLoading) {
        Box(
            modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary)
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(60.dp, 60.dp)
                    .align(Alignment.Center),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    } else {
        content()
    }
}