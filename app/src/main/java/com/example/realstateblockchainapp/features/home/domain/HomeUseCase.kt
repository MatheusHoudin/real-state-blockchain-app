package com.example.realstateblockchainapp.features.home.domain

import com.example.realstateblockchainapp.features.home.mapper.HomeNftMapper
import com.example.realstateblockchainapp.features.home.model.HomeStateModel
import com.example.realstateblockchainapp.shared.repository.NftRepository
import com.example.realstateblockchainapp.shared.api.models.RealStateNFTModel
import com.example.realstateblockchainapp.shared.domain.BaseFlowableUseCase
import com.example.realstateblockchainapp.shared.domain.Result
import com.example.realstateblockchainapp.shared.preferences.PRIVATE_WALLET_KEY
import com.example.realstateblockchainapp.shared.preferences.PUBLIC_WALLET_KEY
import com.example.realstateblockchainapp.shared.preferences.PreferencesRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class HomeUseCase(
    private val nftRepository: NftRepository,
    private val homeNftMapper: HomeNftMapper,
    private val preferencesRepository: PreferencesRepository
) : BaseFlowableUseCase<Unit, HomeStateModel> {
    override fun execute(params: Unit): Flow<Result<HomeStateModel>> =
        flow {
            try {
                val web3Data =
                    coroutineScope { async { nftRepository.fetchRealStateNft() }.await() }
                val nftsResponse =
                    coroutineScope { async { nftRepository.fetchAllNfts() }.await() }

                preferencesRepository.putString(
                    PUBLIC_WALLET_KEY,
                    web3Data.user.address
                )

                val mappedResponse = homeNftMapper.convert(
                    RealStateNFTModel(
                        web3RealStateNft = web3Data,
                        nfts = nftsResponse
                    )
                )
                emit(Result.Success(mappedResponse))
            } catch (e: Throwable) {
                emit(Result.Error(e))
            }
        }.onStart {
            emit(Result.Loading)
        }
}