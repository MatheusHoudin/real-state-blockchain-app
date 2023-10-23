package com.example.realstateblockchainapp.features.home.domain

import com.example.realstateblockchainapp.features.home.mapper.NftDetailsMapper
import com.example.realstateblockchainapp.features.home.model.NftDetailsDomainModel
import com.example.realstateblockchainapp.shared.api.models.FullNftDetailsModel
import com.example.realstateblockchainapp.shared.api.models.RealStateNFTModel
import com.example.realstateblockchainapp.shared.domain.BaseFlowableUseCase
import com.example.realstateblockchainapp.shared.domain.Result
import com.example.realstateblockchainapp.shared.repository.CoinRepository
import com.example.realstateblockchainapp.shared.repository.NftRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class GetNftDetailsUseCase(
    private val nftRepository: NftRepository,
    private val coinRepository: CoinRepository,
    private val nftDetailsMapper: NftDetailsMapper
) : BaseFlowableUseCase<String, NftDetailsDomainModel> {
    override fun execute(params: String): Flow<Result<NftDetailsDomainModel>> = flow {
        try {
            val nftDetails =
                coroutineScope { async { nftRepository.fetchNftDetails(params) }.await() }
            val coinDetails =
                coroutineScope { async { coinRepository.fetchCoinDetails(params) }.await() }

            val mappedResponse = nftDetailsMapper.convert(
                FullNftDetailsModel(
                    nftDetailsModel = nftDetails,
                    coinDetails = coinDetails
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