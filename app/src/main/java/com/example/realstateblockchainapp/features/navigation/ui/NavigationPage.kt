package com.example.realstateblockchainapp.features.navigation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.realstateblockchainapp.features.home.ui.HomePage
import com.example.realstateblockchainapp.features.navigation.model.CreateNftRequest
import com.example.realstateblockchainapp.features.navigation.viewmodel.NavigationViewModel
import com.example.realstateblockchainapp.features.wallet.ui.WalletPage
import com.example.realstateblockchainapp.shared.components.LoadingOrContent
import com.example.realstateblockchainapp.shared.utils.NavConstants
import com.example.realstateblockchainapp.shared.utils.openUrl
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationPage() {

    var navigationSelectedItem by remember {
        mutableIntStateOf(0)
    }
    val navController = rememberNavController()
    val sheetState = rememberModalBottomSheetState()

    val navigationVm = koinViewModel<NavigationViewModel>()
    val state = navigationVm.navigationState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.onPrimary
            ) {
                BottomNavigationItem().bottomNavigationItems()
                    .forEachIndexed { index, navigationItem ->
                        NavigationBarItem(
                            selected = index == navigationSelectedItem,

                            label = {
                                Text(navigationItem.label)
                            },
                            icon = {
                                Icon(
                                    navigationItem.icon,
                                    contentDescription = navigationItem.label
                                )
                            },
                            onClick = {
                                navigationSelectedItem = index
                                navController.navigate(navigationItem.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigationVm.showCreateNftDialog() },
                containerColor = MaterialTheme.colorScheme.onPrimary,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            NavHost(
                navController = navController,
                startDestination = NavConstants.HOME_PAGE
            ) {
                composable(NavConstants.HOME_PAGE) {
                    HomePage()
                }
                composable(NavConstants.WALLET_PAGE) {
                    WalletPage()
                }
            }
            if (state.value.showCreateNftDialog) {
                CreateNftBottomSheet(
                    sheetState,
                    state.value.isCreatingNft,
                    state.value.createNftHash,
                    navigationVm::createNft
                ) {
                    navigationVm.hideCreateNftDialog()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateNftBottomSheet(
    sheetState: SheetState,
    isCreatingNft: Boolean,
    nftHashResult: String?,
    createNft: (CreateNftRequest) -> Unit,
    onDismiss: () -> Unit
) {
    val nftDataUri = remember { mutableStateOf("") }
    val coinName = remember { mutableStateOf("") }
    val coinSymbol = remember { mutableStateOf("") }
    val initialSupply = remember { mutableStateOf("") }
    val lockedAmount = remember { mutableStateOf("") }

    LaunchedEffect("") {
        sheetState.expand()
    }

    val context = LocalContext.current

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState,

        ) {
        LoadingOrContent(
            isLoading = isCreatingNft,
            modifier = Modifier
                .fillMaxWidth(),
            progressColor = MaterialTheme.colorScheme.primary
        ) {
            nftHashResult?.let {
                Button(
                    onClick = {
                        openUrl(context, it)
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp)
                ) {
                    Text(
                        text = "View Transaction",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            } ?: kotlin.run {
                Text(
                    text = "NFT Price: 0.02 ETH",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
                CreateNftText(
                    label = "Nft Uri",
                    value = nftDataUri.value
                ) { nftDataUri.value = it }
                Spacer(modifier = Modifier.height(12.dp))
                CreateNftText(
                    label = "Coin Name",
                    value = coinName.value
                ) { coinName.value = it }
                Spacer(modifier = Modifier.height(12.dp))
                CreateNftText(
                    label = "Coin Symbol",
                    value = coinSymbol.value
                ) { coinSymbol.value = it }
                Spacer(modifier = Modifier.height(12.dp))
                CreateNftText(
                    label = "Initial Supply",
                    value = initialSupply.value,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                ) { initialSupply.value = it }
                Spacer(modifier = Modifier.height(12.dp))
                CreateNftText(
                    label = "Locked Amount",
                    value = lockedAmount.value,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                ) { lockedAmount.value = it }
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        createNft(
                            CreateNftRequest(
                                uri = nftDataUri.value,
                                coinName = coinName.value,
                                coinSymbol = coinSymbol.value,
                                initialSupply = initialSupply.value,
                                lockedAmount = lockedAmount.value
                            )
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp)
                ) {
                    Text(
                        text = "Create NFT",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

        }
    }
}

@Composable
private fun CreateNftText(
    label: String,
    value: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        placeholder = {
            Text(label)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp),
        keyboardOptions = keyboardOptions,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
        )
    )
}