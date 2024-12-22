package com.app.cryptotracker.core.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * ObserveAsEvent observes a Flow as an event in a Composable function.
 *
 * This composable function observes a Flow and triggers the onEvent callback whenever a new value is emitted.
 * It uses LaunchedEffect to collect the Flow within the lifecycle of the composable.
 *
 * @param T The type of the event emitted by the Flow.
 * @param events The Flow to observe.
 * @param key1 An optional key to restart the LaunchedEffect.
 * @param key2 An optional key to restart the LaunchedEffect.
 * @param onEvent The callback to invoke when a new event is emitted.
 */
@Composable
fun <T> ObserveAsEvent(
    events: Flow<T>,
    key1: Any? = null,
    key2: Any? = null,
    onEvent: (T) -> Unit
) {
    // Gets the current lifecycle owner.
    val lifecycleOwner = LocalLifecycleOwner.current

    // Launches an effect that is scoped to the lifecycle of the composable.
    LaunchedEffect(lifecycleOwner.lifecycle, key1, key2) {
        // Repeats the collection of the Flow when the lifecycle is at least STARTED.
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            // Collects the Flow on the main thread.
            withContext(Dispatchers.Main.immediate) {
                // Invokes the onEvent callback for each emitted value.
                events.collect(onEvent)
            }
        }
    }
}