package com.example.realstateblockchainapp.features.navigation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.realstateblockchainapp.features.navigation.domain.CreateNftUseCase
import com.example.realstateblockchainapp.features.navigation.model.CreateNftRequest
import com.example.realstateblockchainapp.shared.BaseViewModel
import com.example.realstateblockchainapp.shared.domain.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NavigationViewModel(
    private val createNftUseCase: CreateNftUseCase
) : BaseViewModel() {
    private val _navigationState: MutableStateFlow<NavigationDomain> =
        MutableStateFlow(NavigationDomain())
    val navigationState: StateFlow<NavigationDomain> = _navigationState.asStateFlow()

    fun createNft(request: CreateNftRequest) {
        viewModelScope.launch {
            createNftUseCase.execute(request).collectLatest { result ->
                when (result) {
                    is Result.Loading -> {
                        _navigationState.value = _navigationState.value.copy(
                            isCreatingNft = true,
                            createNftHash = null
                        )
                    }
                    is Result.Success -> {
                        _navigationState.value = _navigationState.value.copy(
                            isCreatingNft = false,
                            createNftHash = result.data
                        )
                    }
                    is Result.Error -> {
                        _navigationState.value = _navigationState.value.copy(
                            isCreatingNft = false,
                            createNftHash = null
                        )
                    }
                }
            }
        }
    }

    fun showCreateNftDialog() {
        _navigationState.value = _navigationState.value.copy(
            showCreateNftDialog = true
        )
    }

    fun hideCreateNftDialog() {
        _navigationState.value = _navigationState.value.copy(
            showCreateNftDialog = false,
            createNftHash = null
        )
    }
}

data class NavigationDomain(
    val showCreateNftDialog: Boolean = false,
    val isCreatingNft: Boolean = false,
    val createNftHash: String? = null
)