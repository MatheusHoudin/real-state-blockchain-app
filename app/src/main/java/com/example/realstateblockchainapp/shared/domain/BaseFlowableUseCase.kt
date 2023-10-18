package com.example.realstateblockchainapp.shared.domain

import kotlinx.coroutines.flow.Flow

interface BaseFlowableUseCase<PARAMS, RESPONSE> {
    fun execute(params: PARAMS): Flow<Result<RESPONSE>>
}