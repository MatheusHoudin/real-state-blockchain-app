package com.example.realstateblockchainapp.features.home.mapper

import com.example.realstateblockchainapp.features.home.model.HomeStateModel
import com.example.realstateblockchainapp.shared.api.models.RealStateNFTModel
import com.example.realstateblockchainapp.shared.api.models.RealStateNft
import com.example.realstateblockchainapp.shared.mapper.IMapper
import com.example.realstateblockchainapp.shared.utils.convertWeiToEtherUnit

class HomeNftMapper : IMapper<RealStateNFTModel, HomeStateModel> {
    override fun convert(data: RealStateNFTModel): HomeStateModel {
        val web3 = data.web3RealStateNft

        return HomeStateModel(
            contractName = web3.contractName,
            contractAddress = "https://sepolia.etherscan.io/address/${web3.contractAddress}",
            contractSymbol = web3.contractSymbol,
            nftPrice = convertWeiToEtherUnit(web3.nftPrice),
            userEthBalance = convertWeiToEtherUnit(web3.userEthBalance),
            userAddress = "https://sepolia.etherscan.io/address/${web3.user.address}",
            nfts = data.nfts
        )
    }
}