package com.app.cryptotracker.crypto.presentation.coin_list

import com.app.cryptotracker.crypto.presentation.models.CoinUi

/**
 * CoinListAction represents the user actions (Intents) for the coin list screen.
 *
 * This sealed interface defines the possible user actions that can occur on the coin list screen.
 */
sealed interface CoinListAction {
    /**
     * OnCoinClick represents the action of clicking on a coin.
     *
     * @property coinUi The CoinUi object that was clicked.
     */
    data class OnCoinClick(val coinUi: CoinUi) : CoinListAction
}