package com.example.realstateblockchainapp.features.navigation.domain

import com.example.realstateblockchainapp.features.navigation.model.CreateNftRequest
import com.example.realstateblockchainapp.shared.domain.BaseFlowableUseCase
import com.example.realstateblockchainapp.shared.domain.Result
import com.example.realstateblockchainapp.shared.repository.NftRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class CreateNftUseCase(
    private val repository: NftRepository
) : BaseFlowableUseCase<CreateNftRequest, String> {
    override fun execute(params: CreateNftRequest): Flow<Result<String>> =
        flow {
            try {
                val modifierParams = params.copy(
                    initialSupply = "${params.initialSupply}$DECIMALS",
                    lockedAmount = "${params.lockedAmount}$DECIMALS"
                )
                val createNftResponse =
                    coroutineScope { async { repository.createNft(modifierParams) }.await() }

                emit(Result.Success("https://sepolia.etherscan.io/tx/${createNftResponse.hash}"))
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