package com.app.cryptotracker.crypto.presentation.coin_list

import androidx.compose.runtime.Immutable
import com.app.cryptotracker.crypto.presentation.models.CoinUi

/**
 * CoinListState represents the state of the coin list screen.
 *
 * This data class holds the current state of the coin list screen,
 * including whether the data is loading, the list of coins, and the selected coin.
 *
 * @property isLoading Indicates whether the data is currently being loaded.
 * @property coins The list of CoinUi objects to display.
 * @property selectedCoin The currently selected coin, or null if no coin is selected.
 */
@Immutable
data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<CoinUi> = emptyList(),
    val selectedCoin: CoinUi? = null,
)
