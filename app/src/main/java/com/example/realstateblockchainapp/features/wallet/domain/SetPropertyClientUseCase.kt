package com.example.realstateblockchainapp.features.wallet.domain

import com.example.realstateblockchainapp.shared.api.models.GenericTransactionResponse
import com.example.realstateblockchainapp.shared.api.models.SetPropertyClientModel
import com.example.realstateblockchainapp.shared.domain.BaseFlowableUseCase
import com.example.realstateblockchainapp.shared.domain.Result
import com.example.realstateblockchainapp.shared.repository.WalletRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class SetPropertyClientUseCase(
    private val repository: WalletRepository
) : BaseFlowableUseCase<SetPropertyClientModel, GenericTransactionResponse> {
    override fun execute(params: SetPropertyClientModel): Flow<Result<GenericTransactionResponse>> =
        flow {
            try {
                val response =
                    coroutineScope { async { repository.setPropertyClient(params.copy(
                        rentValue = "${params.rentValue}$DECIMALS"
                    )) }.await() }

                emit(Result.Success(response.copy(

                )))
            } catch (e: Throwable) {
                emit(Result.Error(e))
            }
        }.onStart {
            emit(Result.Loading)
        }

    private companion object {
        const val DECIMALS = "000000000000000000"
    }

}
