package com.example.realstateblockchainapp.shared.mapper

interface IMapper<ORIGINAL, RESULT> {
    fun convert(data: ORIGINAL): RESULT
}