package com.example.realstateblockchainapp.shared.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingOrContent(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    progressColor: Color = MaterialTheme.colorScheme.onPrimary,
    content: @Composable () -> Unit
) {
    if (isLoading) {
        Box(
            modifier = modifier
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(60.dp, 60.dp)
                    .align(Alignment.Center),
                color = progressColor
            )
        }
    } else {
        content()
    }
}