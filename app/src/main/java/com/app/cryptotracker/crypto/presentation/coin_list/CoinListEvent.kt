package com.app.cryptotracker.crypto.presentation.coin_list

import com.app.cryptotracker.core.domain.util.NetworkError

/**
 * CoinListEvent represents events that can occur in the coin list screen.
 *
 * This sealed interface defines the possible events that can be emitted by the CoinListViewModel.
 * It's used to communicate events from the ViewModel to the UI.
 */
sealed interface CoinListEvent {
    /**
     * Error represents a network error event.
     *
     * This data class is used to emit a network error event from the ViewModel to the UI.
     *
     * @property error The NetworkError that occurred.
     */
    data class Error(val error: NetworkError) : CoinListEvent
}