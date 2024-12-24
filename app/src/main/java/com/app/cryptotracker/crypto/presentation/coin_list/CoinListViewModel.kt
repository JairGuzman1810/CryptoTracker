package com.app.cryptotracker.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.cryptotracker.core.domain.util.onError
import com.app.cryptotracker.core.domain.util.onSuccess
import com.app.cryptotracker.crypto.domain.CoinDataSource
import com.app.cryptotracker.crypto.presentation.coin_detail.DataPoint
import com.app.cryptotracker.crypto.presentation.models.CoinUi
import com.app.cryptotracker.crypto.presentation.models.toCoinUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * CoinListViewModel is the ViewModel for the coin list screen.
 *
 * This ViewModel is responsible for managing the state of the coin list screen,
 * fetching the list of coins, and handling user actions.
 * It follows the MVI (Model-View-Intent) pattern.
 *
 * @param coinDataSource The data source for fetching coin data.
 */
class CoinListViewModel(
    private val coinDataSource: CoinDataSource
) : ViewModel() {

    /**
     * _state is a MutableStateFlow that holds the current state of the coin list screen.
     *
     * It's private to prevent external modification.
     */
    private val _state = MutableStateFlow(CoinListState())

    /**
     * state is a StateFlow that exposes the current state of the coin list screen.
     *
     * It's derived from _state and is exposed as a read-only property.
     * It starts by loading the coins and emits updates to the UI.
     */
    val state = _state
        .onStart { loadCoins() } // Load coins when the flow starts.
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L), // Keep the flow alive for 5 seconds after the last subscriber unsubscribes.
            CoinListState() // Initial state.
        )

    /**
     * _events is a private Channel used to emit one-off events from the ViewModel to the UI.
     *
     * It's private to prevent external modification and is used internally to send events.
     */
    private val _events = Channel<CoinListEvent>()

    /**
     * events is a Flow that exposes the one-off events emitted by the ViewModel.
     *
     * It's derived from _events and is exposed as a read-only property.
     * The UI can collect this Flow to receive events.
     */
    val events = _events.receiveAsFlow()

    /**
     * onAction handles user actions (Intents) for the coin list screen.
     *
     * This function processes user actions, such as clicking on a coin, and updates the state accordingly.
     *
     * @param action The user action to handle.
     */
    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.OnCoinClick -> {
                // Call the selectCoin function when a coin is clicked.
                selectCoin(action.coinUi)
            }
        }
    }

    /**
     * selectCoin handles the selection of a coin.
     *
     * This function updates the state to reflect the selected coin and fetches the coin's price history.
     *
     * @param coinUi The selected coin.
     */
    private fun selectCoin(coinUi: CoinUi) {
        // Updates the state to reflect the selected coin.
        _state.update { it.copy(selectedCoin = coinUi) }

        // Launch a coroutine to fetch the coin's price history.
        viewModelScope.launch {
            // Fetch the coin's price history from the data source.
            coinDataSource
                .getCoinHistory(
                    coinId = coinUi.id, // The ID of the selected coin.
                    start = ZonedDateTime.now().minusDays(5), // The start date for the history (5 days ago).
                    end = ZonedDateTime.now() // The end date for the history (now).
                )
                .onSuccess { history -> // Handle successful retrieval of history.
                    // Transform the history data into a list of DataPoint objects.
                    val dataPoints = history
                        .sortedBy { it.dateTime } // Sort the history by date and time.
                        .map {
                            DataPoint(
                                x = it.dateTime.hour.toFloat(), // Use the hour of the day as the x-coordinate.
                                y = it.priceUsd.toFloat(), // Use the price in USD as the y-coordinate.
                                xLabel = DateTimeFormatter
                                    .ofPattern("ha\nM/d", Locale.ENGLISH) // Define the format for the x-axis label (hour AM/PM, Month/Day).
                                    .format(it.dateTime) // Format the date and time for the x-axis label.
                            )
                        }

                    // Update the state with the new price history data.
                    _state.update {
                        it.copy(
                            selectedCoin = it.selectedCoin?.copy( // Update the selected coin's data.
                                coinPriceHistory = dataPoints // Set the coin's price history to the newly created data points.
                            )
                        )
                    }
                }
                .onError { error -> // Handle errors during history retrieval.
                    // Send an error event to the UI.
                    _events.send(CoinListEvent.Error(error))
                }
        }
    }

    /**
     * loadCoins fetches the list of coins from the data source.
     *
     * This private function is responsible for fetching the list of coins from the data source
     * and updating the state accordingly.
     */
    private fun loadCoins() {
        viewModelScope.launch {
            // Update the state to loading.
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            // Fetch the coins from the data source.
            coinDataSource
                .getCoins()
                .onSuccess { coins ->
                    // Update the state with the fetched coins.
                    _state.update {
                        it.copy(
                            isLoading = false,
                            coins = coins.map { coin -> coin.toCoinUi() } // Map each coin to its UI representation.
                        )
                    }
                }
                .onError { error ->
                    // Update the state to not loading in case of error.
                    _state.update { it.copy(isLoading = false) }
                    // Send an error event to the UI.
                    _events.send(CoinListEvent.Error(error))
                }
        }
    }
}