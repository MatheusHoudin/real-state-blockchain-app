package com.example.realstateblockchainapp.shared.domain

sealed class Result<out R> {
    data class Success<out R>(val data: R) : Result<R>()

    data class Error<out R>(val exception: Throwable, val data: R? = null) : Result<R>()

    object Loading : Result<Nothing>()
}