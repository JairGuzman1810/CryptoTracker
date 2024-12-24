package com.app.cryptotracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.app.cryptotracker.core.navigation.AdaptiveCoinListDetailPage
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
        // Enables edge-to-edge display. This allows the app to draw behind the system bars (status bar and navigation bar).
        enableEdgeToEdge()
        // Sets the content of the activity using Jetpack Compose.
        setContent {
            CryptoTrackerTheme { // Applies the CryptoTrackerTheme to the app's UI.
                // A Scaffold provides the basic Material Design visual layout structure.
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding -> // The Scaffold fills the entire screen.
                    // The innerPadding is used to ensure that content is not drawn behind the system bars.
                    AdaptiveCoinListDetailPage(
                        modifier = Modifier.padding(innerPadding), // Applies the innerPadding to the AdaptiveCoinListDetailPage.
                        viewModel = koinViewModel() // Injects the CoinListViewModel using Koin.
                    )
                }
            }
        }
    }
}
