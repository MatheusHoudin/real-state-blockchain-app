package com.example.realstateblockchainapp.features.home.mapper

import com.example.realstateblockchainapp.features.home.model.HomeStateModel
import com.example.realstateblockchainapp.shared.api.models.RealStateNft
import com.example.realstateblockchainapp.shared.mapper.IMapper
import com.example.realstateblockchainapp.shared.utils.convertWeiToEtherUnit

class HomeNftMapper : IMapper<RealStateNft, HomeStateModel>{
    override fun convert(data: RealStateNft): HomeStateModel = HomeStateModel(
        contractName = data.contractName,
        contractAddress = "https://sepolia.etherscan.io/address/${data.contractAddress}",
        contractSymbol = data.contractSymbol,
        nftPrice = convertWeiToEtherUnit(data.nftPrice),
        userAddress = "https://sepolia.etherscan.io/address/${data.user.address}"
    )
}