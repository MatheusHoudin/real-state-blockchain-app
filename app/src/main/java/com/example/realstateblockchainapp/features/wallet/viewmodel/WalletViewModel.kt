package com.example.realstateblockchainapp.features.wallet.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.realstateblockchainapp.features.wallet.domain.GetWalletUseCase
import com.example.realstateblockchainapp.features.wallet.model.WalletDomainModel
import com.example.realstateblockchainapp.shared.BaseViewModel
import com.example.realstateblockchainapp.shared.domain.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WalletViewModel(
    private val getWalletUseCase: GetWalletUseCase
) : BaseViewModel() {

    private val _walletState: MutableStateFlow<WalletDomainModel> =
        MutableStateFlow(WalletDomainModel())
    val walletState: StateFlow<WalletDomainModel> = _walletState.asStateFlow()

    init {
        getWalletData()
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

    private fun showLoading() {
        _walletState.value = _walletState.value.copy(
            isLoading = true
        )
    }

    private fun onSuccess(data: WalletDomainModel) {
        _walletState.value = data
    }
}