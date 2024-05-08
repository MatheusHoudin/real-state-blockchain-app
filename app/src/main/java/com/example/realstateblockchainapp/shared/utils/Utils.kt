package com.example.realstateblockchainapp.shared.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun openUrl(context: Context, url: String) {
    val urlIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(url)
    )
    context.startActivity(urlIntent)
}

fun convertWeiToEtherUnit(valueInWei: String, shouldRemove: Boolean = false): String {
    val wei: Double
    if (shouldRemove) {
        wei = valueInWei.substring(0, valueInWei.length - 18).toDouble()
    } else {
        wei = valueInWei.toDouble()
    }
    return wei?.let { value ->
        (value / DECIMALS).toString()
    } ?: "0"
}

const val DECIMALS: Double = 1000000000000000000.0