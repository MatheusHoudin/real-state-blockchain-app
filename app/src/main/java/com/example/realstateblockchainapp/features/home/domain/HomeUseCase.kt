package com.example.realstateblockchainapp.features.home.domain

import com.example.realstateblockchainapp.features.home.mapper.HomeNftMapper
import com.example.realstateblockchainapp.features.home.model.HomeStateModel
import com.example.realstateblockchainapp.features.home.repository.HomeRepository
import com.example.realstateblockchainapp.shared.domain.BaseFlowableUseCase
import com.example.realstateblockchainapp.shared.domain.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class HomeUseCase(
    private val homeRepository: HomeRepository,
    private val homeNftMapper: HomeNftMapper
) : BaseFlowableUseCase<Unit, HomeStateModel> {
    override fun execute(params: Unit): Flow<Result<HomeStateModel>> =
        flow {
            try {
                val nftResponse =
                    coroutineScope { async { homeRepository.fetchRealStateNft() }.await() }
                val mappedResponse = homeNftMapper.convert(nftResponse)
                emit(Result.Success(mappedResponse))
            } catch (e: Throwable) {
                emit(Result.Error(e))
            }
        }.onStart {
            emit(Result.Loading)
        }
}