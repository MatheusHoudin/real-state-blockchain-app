package com.example.realstateblockchainapp.features.wallet.domain

import com.example.realstateblockchainapp.features.wallet.model.WalletDomainModel
import com.example.realstateblockchainapp.shared.domain.BaseFlowableUseCase
import com.example.realstateblockchainapp.shared.domain.Result
import com.example.realstateblockchainapp.shared.repository.WalletRepository
import com.example.realstateblockchainapp.shared.utils.convertWeiToEtherUnit
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class GetWalletUseCase(
    private val repository: WalletRepository
) : BaseFlowableUseCase<Unit, WalletDomainModel> {
    override fun execute(params: Unit): Flow<Result<WalletDomainModel>> = flow {
        try {
            val walletResponse =
                coroutineScope { async { repository.getWalletData() }.await() }

            emit(Result.Success(WalletDomainModel(
                isLoading = false,
                nfts = walletResponse.nfts,
                tokens = walletResponse.tokens.map {
                    it.copy(
                        contractAddress = "https://sepolia.etherscan.io/address/${it.contractAddress}",
                        rawBalance = convertWeiToEtherUnit(it.balance)
                    )
                }
            )))
        } catch (e: Throwable) {
            emit(Result.Error(e))
        }
    }.onStart {
        emit(Result.Loading)
    }
}