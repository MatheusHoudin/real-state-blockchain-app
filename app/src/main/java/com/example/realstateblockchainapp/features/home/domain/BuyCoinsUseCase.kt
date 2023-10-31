package com.example.realstateblockchainapp.features.home.domain

import com.example.realstateblockchainapp.shared.api.models.BuyCoinsRequest
import com.example.realstateblockchainapp.shared.api.models.GenericTransactionResponse
import com.example.realstateblockchainapp.shared.domain.BaseFlowableUseCase
import com.example.realstateblockchainapp.shared.domain.Result
import com.example.realstateblockchainapp.shared.repository.CoinRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class BuyCoinsUseCase(
    private val coinRepository: CoinRepository
) : BaseFlowableUseCase<BuyCoinsRequest, GenericTransactionResponse> {
    override fun execute(params: BuyCoinsRequest): Flow<Result<GenericTransactionResponse>> = flow {
        try {
            val buyCoinResponse = coroutineScope { async { coinRepository.buyCoins(params) }.await() }

            emit(Result.Success(buyCoinResponse))
        } catch (e: Throwable) {
            emit(Result.Error(e))
        }
    }.onStart {
        emit(Result.Loading)
    }
}