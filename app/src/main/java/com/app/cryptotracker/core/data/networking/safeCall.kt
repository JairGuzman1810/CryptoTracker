package com.app.cryptotracker.core.data.networking

import com.app.cryptotracker.core.domain.util.NetworkError
import com.app.cryptotracker.core.domain.util.Result
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

/**
 * safeCall safely executes a network request and handles potential exceptions.
 *
 * This suspend inline function executes a network request provided by the execute lambda.
 * It handles potential exceptions that might occur before, during, or after the request,
 * such as UnresolvedAddressException (no internet), SerializationException, and other general exceptions.
 * It then uses responseToResult to convert the HttpResponse to a Result object.
 *
 * @param T The type of the expected response body.
 * @param execute A lambda that executes the network request and returns an HttpResponse.
 * @return A Result object representing either a successful response with the parsed body or a NetworkError.
 */
suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, NetworkError> {
    // Executes the network request and handles potential exceptions.
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        // Returns a NO_INTERNET_CONNECTION error if the address cannot be resolved.
        return Result.Error(NetworkError.NO_INTERNET_CONNECTION)
    } catch (e: SerializationException) {
        // Returns a SERIALIZATION_ERROR if there is an issue with serialization.
        return Result.Error(NetworkError.SERIALIZATION_ERROR)
    } catch (e: Exception) {
        // Ensures the coroutine is still active before returning an error.
        coroutineContext.ensureActive()
        // Returns an UNKNOWN_NETWORK_ERROR for any other exceptions.
        return Result.Error(NetworkError.UNKNOWN_NETWORK_ERROR)
    }

    // Converts the HttpResponse to a Result object.
    return responseToResult(response)
}