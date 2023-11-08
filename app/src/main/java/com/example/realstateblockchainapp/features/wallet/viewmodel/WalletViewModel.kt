package com.example.realstateblockchainapp.features.wallet.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.realstateblockchainapp.features.home.domain.BuyCoinsUseCase
import com.example.realstateblockchainapp.features.home.domain.GetNftDetailsUseCase
import com.example.realstateblockchainapp.features.home.model.BuyCoinState
import com.example.realstateblockchainapp.features.home.model.NftDetailsDomainModel
import com.example.realstateblockchainapp.features.wallet.domain.GetWalletUseCase
import com.example.realstateblockchainapp.features.wallet.domain.SetPropertyClientUseCase
import com.example.realstateblockchainapp.features.wallet.model.WalletDomainModel
import com.example.realstateblockchainapp.shared.BaseViewModel
import com.example.realstateblockchainapp.shared.api.models.BuyCoinsRequest
import com.example.realstateblockchainapp.shared.api.models.SetPropertyClientModel
import com.example.realstateblockchainapp.shared.domain.Result
import com.example.realstateblockchainapp.shared.utils.AppConstants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WalletViewModel(
    private val getWalletUseCase: GetWalletUseCase,
    private val getNftDetailsUseCase: GetNftDetailsUseCase,
    private val buyCoinsUseCase: BuyCoinsUseCase,
    private val setPropertyClientUseCase: SetPropertyClientUseCase,
) : BaseViewModel() {

    private val _walletState: MutableStateFlow<WalletDomainModel> =
        MutableStateFlow(WalletDomainModel())
    val walletState: StateFlow<WalletDomainModel> = _walletState.asStateFlow()

    init {
        getWalletData()
    }

    fun setPropertyClient(model: SetPropertyClientModel) {
        viewModelScope.launch {
            setPropertyClientUseCase.execute(model).collectLatest { result ->
                when (result) {
                    is Result.Loading -> {
                        _walletState.value = _walletState.value.copy(
                            isAddingClientToProperty = true
                        )
                    }

                    is Result.Success -> {
                        _walletState.value = _walletState.value.copy(
                            isAddingClientToProperty = false,
                            addClientPropertyHash = "https://sepolia.etherscan.io/tx/${result.data.hash}"
                        )
                    }

                    is Result.Error -> {
                        _walletState.value = _walletState.value.copy(
                            isAddingClientToProperty = false
                        )
                    }
                }
            }
        }
    }

    private fun getWalletData() {
        viewModelScope.launch {
            getWalletUseCase.execute(Unit).collectLatest { result ->
                when (result) {
                    is Result.Loading -> {
                        showLoading()
                    }

                    is Result.Success -> {
                        onSuccess(result.data)
                    }

                    is Result.Error -> {

                    }
                }
            }
        }
    }

    fun onCloseNftDetails() {
        _walletState.value = _walletState.value.copy(
            nftDetails = null
        )
    }

    fun showBuyCoinDialog() {
        _walletState.value = _walletState.value.copy(
            buyCoinState = BuyCoinState()
        )
    }

    fun onChangeCoinsQuantity(quantity: String) {
        quantity.toIntOrNull()?.let {
            _walletState.value = _walletState.value.copy(
                buyCoinState = _walletState.value.buyCoinState?.copy(
                    valueInEth = "${(it * AppConstants.COIN_PRICE_IN_ETH).toBigDecimal()} ETH"
                )
            )
        }
    }

    fun hideBuyCoinDialog() {
        _walletState.value = _walletState.value.copy(
            buyCoinState = null
        )
    }

    fun buyCoins(coinsQuantity: String) {
        coinsQuantity.toIntOrNull()?.let { quantity ->
            val ethValue = (quantity * AppConstants.COIN_PRICE_IN_ETH).toString()
            viewModelScope.launch {
                buyCoinsUseCase.execute(
                    BuyCoinsRequest(
                        ethValue = ethValue,
                        nftId = _walletState.value.nftDetails?.nftId.orEmpty()
                    )
                ).collectLatest { result ->
                    when (result) {
                        is Result.Loading -> {
                            _walletState.value = _walletState.value.copy(
                                buyCoinState = _walletState.value.buyCoinState?.copy(
                                    buyCoinLoading = true
                                )
                            )
                        }

                        is Result.Success -> {
                            _walletState.value = _walletState.value.copy(
                                buyCoinState = _walletState.value.buyCoinState?.copy(
                                    buyCoinLoading = false,
                                    resultMessage = "https://sepolia.etherscan.io/tx/${result.data.hash}"
                                )
                            )
                        }

                        is Result.Error -> {
                            _walletState.value = _walletState.value.copy(
                                buyCoinState = _walletState.value.buyCoinState?.copy(
                                    buyCoinLoading = false
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    fun openNftDetails(nftId: String) {
        viewModelScope.launch {
            getNftDetailsUseCase.execute(nftId).collectLatest { result ->
                when (result) {
                    is Result.Loading -> {
                        _walletState.value = _walletState.value.copy(
                            nftDetails = NftDetailsDomainModel(isLoading = true, isOwner = true)
                        )
                    }

                    is Result.Success -> {
                        _walletState.value = _walletState.value.copy(
                            nftDetails = result.data.copy(isLoading = false)
                        )
                    }

                    is Result.Error -> {
                        _walletState.value = _walletState.value.copy(
                            nftDetails = NftDetailsDomainModel(isLoading = false, isOwner = true)
                        )
                    }
                }
            }
        }
    }

    private fun showLoading() {
        _walletState.value = _walletState.value.copy(
            isLoading = true
        )
    }

    private fun onSuccess(data: WalletDomainModel) {
        _walletState.value = data
    }
}