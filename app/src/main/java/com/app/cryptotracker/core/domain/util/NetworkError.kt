package com.app.cryptotracker.core.domain.util

/**
 * NetworkError is an enum class that represents different types of network errors.
 *
 * This enum is used to represent errors that can occur during network operations in the domain layer.
 * It provides specific error types for common network issues, allowing for better error handling and reporting.
 */
enum class NetworkError : Error {
    /**
     * REQUEST_TIMEOUT indicates that a network request timed out.
     */
    REQUEST_TIMEOUT,

    /**
     * TOO_MANY_REQUESTS indicates that the client has made too many requests in a short period of time.
     */
    TOO_MANY_REQUESTS,

    /**
     * NO_INTERNET_CONNECTION indicates that the device is not connected to the internet.
     */
    NO_INTERNET_CONNECTION,

    /**
     * SERVER_ERROR indicates that the server encountered an error while processing the request.
     */
    SERVER_ERROR,

    /**
     * SERIALIZATION_ERROR indicates that there was an error while serializing or deserializing data during the network operation.
     */
    SERIALIZATION_ERROR,

    /**
     * UNKNOWN_NETWORK_ERROR indicates an unknown network error occurred.
     */
    UNKNOWN_NETWORK_ERROR
}