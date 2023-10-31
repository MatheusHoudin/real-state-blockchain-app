package com.example.realstateblockchainapp.features.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.realstateblockchainapp.features.home.domain.BuyCoinsUseCase
import com.example.realstateblockchainapp.features.home.domain.GetNftDetailsUseCase
import com.example.realstateblockchainapp.features.home.domain.HomeUseCase
import com.example.realstateblockchainapp.features.home.model.BuyCoinState
import com.example.realstateblockchainapp.features.home.model.HomeStateModel
import com.example.realstateblockchainapp.features.home.model.NftDetailsDomainModel
import com.example.realstateblockchainapp.shared.BaseViewModel
import com.example.realstateblockchainapp.shared.api.models.BuyCoinsRequest
import com.example.realstateblockchainapp.shared.api.models.NftModel
import com.example.realstateblockchainapp.shared.domain.Result
import com.example.realstateblockchainapp.shared.utils.AppConstants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.NumberFormatException

class HomeViewModel(
    private val homeUseCase: HomeUseCase,
    private val getNftDetailsUseCase: GetNftDetailsUseCase,
    private val buyCoinsUseCase: BuyCoinsUseCase
) : BaseViewModel() {

    private val _homeState: MutableStateFlow<HomeStateModel> =
        MutableStateFlow(HomeStateModel())
    val homeState: StateFlow<HomeStateModel> = _homeState.asStateFlow()

    init {
        fetchNftData()
    }

    fun fetchNftData() {
        viewModelScope.launch {
            homeUseCase.execute(Unit).collectLatest { result ->
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
        _homeState.value = _homeState.value.copy(
            nftDetails = null
        )
    }

    fun showBuyCoinDialog() {
        _homeState.value = _homeState.value.copy(
            buyCoinState = BuyCoinState()
        )
    }

    fun onChangeCoinsQuantity(quantity: String) {
        quantity.toIntOrNull()?.let {
            _homeState.value = _homeState.value.copy(
                buyCoinState = _homeState.value.buyCoinState?.copy(
                    valueInEth = "${(it * AppConstants.COIN_PRICE_IN_ETH).toBigDecimal()} ETH"
                )
            )
        }
    }

    fun hideBuyCoinDialog() {
        _homeState.value = _homeState.value.copy(
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
                        nftId = _homeState.value.nftDetails?.nftId.orEmpty()
                    )
                ).collectLatest { result ->
                    when (result) {
                        is Result.Loading -> {
                            _homeState.value = _homeState.value.copy(
                                buyCoinState = _homeState.value.buyCoinState?.copy(
                                    buyCoinLoading = true
                                )
                            )
                        }
                        is Result.Success -> {
                            _homeState.value = _homeState.value.copy(
                                buyCoinState = _homeState.value.buyCoinState?.copy(
                                    buyCoinLoading = false,
                                    resultMessage = "https://sepolia.etherscan.io/tx/${result.data.hash}"
                                )
                            )
                        }
                        is Result.Error -> {
                            _homeState.value = _homeState.value.copy(
                                buyCoinState = _homeState.value.buyCoinState?.copy(
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
                        _homeState.value = _homeState.value.copy(
                            nftDetails = NftDetailsDomainModel(isLoading = true)
                        )
                    }

                    is Result.Success -> {
                        _homeState.value = _homeState.value.copy(
                            nftDetails = result.data.copy(isLoading = false)
                        )
                    }

                    is Result.Error -> {
                        _homeState.value = _homeState.value.copy(
                            nftDetails = NftDetailsDomainModel(isLoading = false)
                        )
                    }
                }
            }
        }
    }

    private fun showLoading() {
        _homeState.value = _homeState.value.copy(
            isLoading = true
        )
    }

    private fun onSuccess(data: HomeStateModel) {
        _homeState.value = data
    }
}