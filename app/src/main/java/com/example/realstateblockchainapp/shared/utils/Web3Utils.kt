package com.example.realstateblockchainapp.shared.utils

import java.math.BigDecimal
import java.math.BigInteger

fun convertWeiToEtherUnit(valueInWei: String): String {
    val wei = valueInWei.toDoubleOrNull()
    return wei?.let { value ->
        (value / DECIMALS).toString()
    } ?: "0"
}

const val DECIMALS: Double = 1000000000000000000.0