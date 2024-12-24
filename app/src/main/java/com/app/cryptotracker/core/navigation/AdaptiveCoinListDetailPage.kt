package com.app.cryptotracker.core.navigation

import android.widget.Toast
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.cryptotracker.core.presentation.util.ObserveAsEvent
import com.app.cryptotracker.core.presentation.util.toString
import com.app.cryptotracker.crypto.presentation.coin_detail.CoinDetailScreen
import com.app.cryptotracker.crypto.presentation.coin_list.CoinListAction
import com.app.cryptotracker.crypto.presentation.coin_list.CoinListEvent
import com.app.cryptotracker.crypto.presentation.coin_list.CoinListScreen
import com.app.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * AdaptiveCoinListDetailPage is a composable function that displays a list of coins and their details
 * in an adaptive layout using `NavigableListDetailPaneScaffold`.
 *
 * This composable uses the `CoinListViewModel` to manage the state and handle user actions.
 * It adapts to different screen sizes by showing either a list, a detail view, or both.
 *
 * @param modifier The modifier to apply to the layout.
 * @param viewModel The `CoinListViewModel` instance to use for managing the coin list state.
 */
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptiveCoinListDetailPage(
    modifier: Modifier = Modifier,
    viewModel: CoinListViewModel = koinViewModel() // Injects the CoinListViewModel using Koin.
) {

    // Collects the state from the ViewModel as a Compose state.
    val state by viewModel.state.collectAsStateWithLifecycle()

    // Gets the current context for displaying Toast messages.
    val context = LocalContext.current
    // Observes events from the ViewModel.
    ObserveAsEvent(events = viewModel.events) { event ->
        // Handles different types of events.
        when (event) {
            is CoinListEvent.Error -> {
                // Displays a Toast message for network errors.
                Toast.makeText(
                    context,
                    event.error.toString(context), // Converts the error to a user-friendly string.
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // Creates a navigator for the ListDetailPaneScaffold.
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    // Creates the NavigableListDetailPaneScaffold layout.
    NavigableListDetailPaneScaffold(
        navigator = navigator, // Provides the navigator for handling pane transitions.
        listPane = { // Defines the content of the list pane.
            AnimatedPane { // Uses AnimatedPane for smooth transitions.
                CoinListScreen( // Displays the list of coins.
                    state = state, // Provides the current state to the CoinListScreen.
                    onAction = { action -> // Handles actions from the CoinListScreen.
                        viewModel.onAction(action) // Delegates the action to the ViewModel.
                        when (action) {
                            is CoinListAction.OnCoinClick -> {
                                // Navigates to the detail pane when a coin is clicked.
                                navigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.Detail, // Specifies the detail pane as the target.
                                )
                            }
                        }
                    }
                )
            }
        },
        detailPane = { // Defines the content of the detail pane.
            AnimatedPane { // Uses AnimatedPane for smooth transitions.
                CoinDetailScreen(state = state) // Displays the details of the selected coin.
            }
        },
        modifier = modifier // Applies the provided modifier to the layout.
    )
}