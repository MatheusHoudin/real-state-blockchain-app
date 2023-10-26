package com.example.realstateblockchainapp.features.home.mapper

import com.example.realstateblockchainapp.features.home.model.CoinDetailsDomainModel
import com.example.realstateblockchainapp.features.home.model.NftDetailsDomainModel
import com.example.realstateblockchainapp.shared.api.models.FullNftDetailsModel
import com.example.realstateblockchainapp.shared.api.models.PropertyClient
import com.example.realstateblockchainapp.shared.mapper.IMapper
import com.example.realstateblockchainapp.shared.utils.convertWeiToEtherUnit

class NftDetailsMapper : IMapper<FullNftDetailsModel, NftDetailsDomainModel> {
    override fun convert(data: FullNftDetailsModel): NftDetailsDomainModel = NftDetailsDomainModel(
        nftId = data.nftDetailsModel.nftId,
        coinAddress = "https://sepolia.etherscan.io/address/${data.nftDetailsModel.coinAddress}",
        propertyClient = if (data.nftDetailsModel.propertyClient.client != "0x0000000000000000000000000000000000000000") PropertyClient(
            client = "https://sepolia.etherscan.io/address/${data.nftDetailsModel.propertyClient.client}",
            value = convertWeiToEtherUnit(data.nftDetailsModel.propertyClient.value)
        ) else null,
        metadata = data.nftDetailsModel.nftData,
        coinDetails = CoinDetailsDomainModel(
            address = "https://sepolia.etherscan.io/address/${data.coinDetails.address}",
            name = data.coinDetails.name,
            symbol = data.coinDetails.symbol,
            totalSupply = convertWeiToEtherUnit(data.coinDetails.totalSupply),
            lockedAmount = convertWeiToEtherUnit(data.coinDetails.lockedAmount),
            availableTokenAmount = convertWeiToEtherUnit(data.coinDetails.availableTokenAmount),
            totalRentIncomeReceived = convertWeiToEtherUnit(data.coinDetails.totalRentIncomeReceived),
        )
    )
}