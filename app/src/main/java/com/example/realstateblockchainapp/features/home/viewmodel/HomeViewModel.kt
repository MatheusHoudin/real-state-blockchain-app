package com.example.realstateblockchainapp.features.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.realstateblockchainapp.features.home.domain.HomeUseCase
import com.example.realstateblockchainapp.features.home.mapper.HomeNftMapper
import com.example.realstateblockchainapp.features.home.model.HomeStateModel
import com.example.realstateblockchainapp.features.home.repository.HomeRepository
import com.example.realstateblockchainapp.shared.BaseViewModel
import com.example.realstateblockchainapp.shared.domain.Result
import com.example.realstateblockchainapp.shared.navigation.NavigationAction
import com.example.realstateblockchainapp.shared.preferences.PRIVATE_WALLET_KEY
import com.example.realstateblockchainapp.shared.preferences.PreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeUseCase: HomeUseCase
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

    private fun showLoading() {
        _homeState.value = _homeState.value.copy(
            isLoading = true
        )
    }

    private fun onSuccess(data: HomeStateModel) {
        _homeState.value = data
    }
}