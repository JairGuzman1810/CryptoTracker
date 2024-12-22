package com.app.cryptotracker.crypto.presentation.coin_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.app.cryptotracker.crypto.presentation.coin_list.components.CoinListItem
import com.app.cryptotracker.crypto.presentation.coin_list.components.previewCoin
import com.app.cryptotracker.ui.theme.CryptoTrackerTheme

/**
 * CoinListScreen is a composable function that displays a list of coins.
 *
 * This composable displays a list of coins, with a loading indicator while the data is being fetched.
 * It uses a LazyColumn to efficiently display a potentially large list of coins.
 *
 * @param state The CoinListState object containing the data to display.
 * @param onAction A lambda function to handle user actions on the coin list screen.
 * @param modifier The modifier to be applied to the root layout of the coin list screen.
 */
@Composable
fun CoinListScreen(
    state: CoinListState,
    onAction: (CoinListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    // Displays a loading indicator if the data is still loading.
    if (state.isLoading) {
        // Box to center the loading indicator.
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Displays a circular progress indicator.
            CircularProgressIndicator()
        }
    } else {
        // Displays the list of coins.
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            // Adds space between the coin items.
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // Iterates over the list of coins.
            items(state.coins) { coinUi ->
                // Displays a single coin item.
                CoinListItem(
                    coin = coinUi,
                    onClick = {
                        // Call the onAction lambda with the OnCoinClick action.
                        onAction(CoinListAction.OnCoinClick(coinUi))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                // Adds a horizontal divider between the coin items.
                HorizontalDivider()
            }
        }
    }
}

/**
 * CoinListScreenPreview is a composable function that previews the CoinListScreen.
 *
 * This composable is used to preview the CoinListScreen in both light and dark themes.
 */
@PreviewLightDark
@Composable
private fun CoinListScreenPreview() {
    CryptoTrackerTheme {
        CoinListScreen(
            state = CoinListState(
                coins = (1..100).map {
                    previewCoin.copy(id = it.toString())
                }
            ),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
            onAction = {}
        )
    }
}