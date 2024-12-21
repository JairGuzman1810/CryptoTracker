package com.app.cryptotracker.core.data.networking

import com.app.cryptotracker.core.domain.util.NetworkError
import com.app.cryptotracker.core.domain.util.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

/**
 * responseToResult converts an HttpResponse to a Result object.
 *
 * This suspend inline function takes an HttpResponse from the Ktor client and converts it into a Result object,
 * handling different HTTP status codes and potential exceptions during response body transformation.
 *
 * @param T The type of the expected response body.
 * @param response The HttpResponse to convert.
 * @return A Result object representing either a successful response with the parsed body or a NetworkError.
 */
suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, NetworkError> {
    // Checks the HTTP status code of the response.
    return when (response.status.value) {
        // Successful response (200-299).
        in 200..299 -> {
            // Attempts to parse the response body.
            try {
                // Returns a Success result with the parsed body.
                Result.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                // Returns a SerializationError if the body cannot be parsed.
                Result.Error(NetworkError.SERIALIZATION_ERROR)
            }
        }
        // Request timeout (408).
        408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
        // Too many requests (429).
        429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
        // Server error (500-599).
        in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
        // Unknown network error (any other status code).
        else -> Result.Error(NetworkError.UNKNOWN_NETWORK_ERROR)
    }
}