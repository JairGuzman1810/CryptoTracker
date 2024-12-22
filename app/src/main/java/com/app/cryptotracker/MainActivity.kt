package com.app.cryptotracker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.cryptotracker.core.presentation.util.ObserveAsEvent
import com.app.cryptotracker.core.presentation.util.toString
import com.app.cryptotracker.crypto.presentation.coin_detail.CoinDetailScreen
import com.app.cryptotracker.crypto.presentation.coin_list.CoinListEvent
import com.app.cryptotracker.crypto.presentation.coin_list.CoinListScreen
import com.app.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import com.app.cryptotracker.ui.theme.CryptoTrackerTheme
import org.koin.androidx.compose.koinViewModel

/**
 * MainActivity is the main activity of the CryptoTracker app.
 *
 * This activity serves as the entry point for the application.
 * It sets up the UI using Jetpack Compose, initializes the CoinListViewModel,
 * and handles events emitted by the ViewModel, such as network errors.
 */
class MainActivity : ComponentActivity() {

    /**
     * onCreate is called when the activity is starting.
     *
     * This function enables edge-to-edge display, sets the content using Jetpack Compose,
     * initializes the CoinListViewModel, collects the state, and observes events
     * to handle network errors and display them to the user.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState.
     *                           Note: Otherwise it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enables edge-to-edge display.
        enableEdgeToEdge()
        // Sets the content of the activity using Jetpack Compose.
        setContent {
            CryptoTrackerTheme {
                // A Scaffold provides the basic Material Design visual layout structure.
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Initializes the CoinListViewModel using Koin.
                    val viewModel = koinViewModel<CoinListViewModel>()
                    // Collects the state from the ViewModel.
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
                                    event.error.toString(context),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    // Conditional UI based on the state.
                    when {
                        // If a coin is selected, display the CoinDetailScreen.
                        state.selectedCoin != null -> {
                            CoinDetailScreen(
                                state = state,
                                modifier = Modifier.padding(innerPadding),
                            )
                        }
                        // Otherwise, display the CoinListScreen.
                        else -> {
                            // Displays the CoinListScreen with the current state.
                            CoinListScreen(
                                state = state,
                                modifier = Modifier.padding(innerPadding),
                                onAction = viewModel::onAction // Passes the onAction lambda to the CoinListScreen.
                            )
                        }
                    }
                }
            }
        }
    }
}
