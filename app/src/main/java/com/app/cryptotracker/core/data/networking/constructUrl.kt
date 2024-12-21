package com.app.cryptotracker.core.data.networking

import com.app.cryptotracker.BuildConfig

/**
 * constructUrl constructs a complete URL from a given URL string.
 *
 * This function takes a URL string and ensures it's a complete URL by prepending the BASE_URL from BuildConfig if necessary.
 * It handles cases where the URL is already complete, starts with a slash, or is a relative path.
 *
 * @param url The URL string to construct.
 * @return The complete URL string.
 */
fun constructUrl(url: String): String {
    // Checks if the URL already contains the BASE_URL.
    return when {
        // If the URL already contains the BASE_URL, it's considered complete.
        url.contains(BuildConfig.BASE_URL) -> url
        // If the URL starts with a slash, it's considered a relative path from the BASE_URL.
        url.startsWith("/") -> BuildConfig.BASE_URL + url.drop(1)
        // Otherwise, it's considered a relative path and the BASE_URL is prepended.
        else -> BuildConfig.BASE_URL + url
    }
}