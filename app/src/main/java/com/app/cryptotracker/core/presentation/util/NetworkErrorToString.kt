package com.app.cryptotracker.core.presentation.util

import android.content.Context
import com.app.cryptotracker.R
import com.app.cryptotracker.core.domain.util.NetworkError

/**
 * toString converts a NetworkError to a user-friendly string.
 *
 * This function takes a NetworkError and a Context and returns a string representation of the error
 * that is suitable for displaying to the user.
 *
 * @receiver The NetworkError to convert to a string.
 * @param context The Context used to retrieve string resources.
 * @return A user-friendly string representation of the NetworkError.
 */
fun NetworkError.toString(context: Context): String {
    // Determines the string resource ID based on the NetworkError.
    val resId = when (this) {
        NetworkError.REQUEST_TIMEOUT -> R.string.error_request_timeout
        NetworkError.TOO_MANY_REQUESTS -> R.string.error_too_many_requests
        NetworkError.NO_INTERNET_CONNECTION -> R.string.error_no_internet
        NetworkError.SERVER_ERROR -> R.string.error_unknown
        NetworkError.SERIALIZATION_ERROR -> R.string.error_serialization
        NetworkError.UNKNOWN_NETWORK_ERROR -> R.string.error_unknown
    }
    // Returns the string from the resource.
    return context.getString(resId)
}