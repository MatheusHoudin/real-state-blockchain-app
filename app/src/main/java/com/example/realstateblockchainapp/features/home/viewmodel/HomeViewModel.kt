package com.example.realstateblockchainapp.features.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.realstateblockchainapp.features.home.domain.GetNftDetailsUseCase
import com.example.realstateblockchainapp.features.home.domain.HomeUseCase
import com.example.realstateblockchainapp.features.home.model.HomeStateModel
import com.example.realstateblockchainapp.features.home.model.NftDetailsDomainModel
import com.example.realstateblockchainapp.shared.BaseViewModel
import com.example.realstateblockchainapp.shared.api.models.NftModel
import com.example.realstateblockchainapp.shared.domain.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeUseCase: HomeUseCase,
    private val getNftDetailsUseCase: GetNftDetailsUseCase
) : BaseViewModel() {

    private val _homeState: MutableStateFlow<HomeStateModel> =
        MutableStateFlow(HomeStateModel())
    val homeState: StateFlow<HomeStateModel> = _homeState.asStateFlow()

    init {
        fetchNftData()
    }

    private fun fetchNftData() {
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