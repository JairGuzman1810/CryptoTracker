package com.app.cryptotracker.core.data.networking

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * HttpClientFactory is a factory object for creating and configuring HttpClient instances.
 *
 * This object provides a centralized way to create HttpClient instances with consistent configurations,
 * such as logging, content negotiation, and default request settings.
 */
object HttpClientFactory {

    /**
     * create creates and configures an HttpClient instance.
     *
     * This function creates an HttpClient instance with the specified engine and configures it with:
     * - Logging: Logs all requests and responses using the Android logger.
     * - Content Negotiation: Configures JSON serialization with the ability to ignore unknown keys.
     * - Default Request: Sets the default content type to JSON.
     *
     * @param engine The HttpClientEngine to use for the HttpClient instance.
     * @return A configured HttpClient instance.
     */
    fun create(engine: HttpClientEngine): HttpClient {
        // Creates an HttpClient instance with the specified engine.
        return HttpClient(engine) {
            // Installs the Logging plugin.
            install(Logging) {
                // Sets the log level to ALL to log all requests and responses.
                level = LogLevel.ALL
                // Uses the Android logger.
                logger = Logger.ANDROID
            }

            // Installs the ContentNegotiation plugin.
            install(ContentNegotiation) {
                // Configures JSON serialization.
                json(
                    json = Json {
                        // Ignores unknown keys during deserialization.
                        ignoreUnknownKeys = true
                    }
                )
            }

            // Configures default request settings.
            defaultRequest {
                // Sets the default content type to JSON.
                contentType(ContentType.Application.Json)
            }
        }
    }
}