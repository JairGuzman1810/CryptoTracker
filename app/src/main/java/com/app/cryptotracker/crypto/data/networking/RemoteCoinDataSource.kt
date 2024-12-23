package com.app.cryptotracker.crypto.data.networking

import com.app.cryptotracker.core.data.networking.constructUrl
import com.app.cryptotracker.core.data.networking.safeCall
import com.app.cryptotracker.core.domain.util.NetworkError
import com.app.cryptotracker.core.domain.util.Result
import com.app.cryptotracker.core.domain.util.map
import com.app.cryptotracker.crypto.data.mappers.toCoin
import com.app.cryptotracker.crypto.data.mappers.toCoinPrice
import com.app.cryptotracker.crypto.data.networking.dto.CoinHistoryDto
import com.app.cryptotracker.crypto.data.networking.dto.CoinsResponseDto
import com.app.cryptotracker.crypto.domain.Coin
import com.app.cryptotracker.crypto.domain.CoinDataSource
import com.app.cryptotracker.crypto.domain.CoinPrice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * RemoteCoinDataSource is a remote data source for fetching coin data from an API.
 *
 * This class implements the CoinDataSource interface and uses the Ktor HttpClient to fetch coin data from a remote API.
 * It handles the network request, maps the response to the domain model, and returns a Result object.
 *
 * @param httpClient The Ktor HttpClient instance used for making network requests.
 */
class RemoteCoinDataSource(
    private val httpClient: HttpClient
) : CoinDataSource {

    /**
     * getCoins fetches a list of coins from the remote API.
     *
     * This suspend function makes a network request to the /assets endpoint to retrieve a list of coins.
     * It uses the safeCall function to handle potential network errors and then maps the response to a list of Coin objects.
     *
     * @return A Result object containing either a list of Coin objects or a NetworkError.
     */
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        // Safely executes the network request.
        return safeCall<CoinsResponseDto> {
            // Makes a GET request to the /assets endpoint.
            httpClient.get(
                urlString = constructUrl("/assets")
            )
        }.map { response ->
            // Maps the CoinResponseDto to a list of Coin objects.
            response.data.map { it.toCoin() }
        }
    }

    /**
     * getCoinHistory fetches the price history of a specific coin from the remote API.
     *
     * This suspend function makes a network request to the /assets/{coinId}/history endpoint to retrieve the price history of a coin.
     * It uses the safeCall function to handle potential network errors and then maps the response to a list of CoinPrice objects.
     *
     * @param coinId The ID of the coin for which to fetch the price history.
     * @param start The start of the time range for which to fetch the price history.
     * @param end The end of the time range for which to fetch the price history.
     * @return A Result object containing either a list of CoinPrice objects or a NetworkError.
     */
    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        // Converts the start ZonedDateTime to milliseconds since the epoch in UTC.
        val startMillis = start
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()

        // Converts the end ZonedDateTime to milliseconds since the epoch in UTC.
        val endMillis = end
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()

        // Safely executes the network request.
        return safeCall<CoinHistoryDto> {
            // Makes a GET request to the /assets/{coinId}/history endpoint.
            httpClient.get(
                urlString = constructUrl("/assets/$coinId/history")
            ) {
                // Sets the interval parameter to "h6" (6 hours).
                parameter("interval", "h6")
                // Sets the start parameter to the start time in milliseconds.
                parameter("start", startMillis)
                // Sets the end parameter to the end time in milliseconds.
                parameter("end", endMillis)
            }

        }.map { response ->
            // Maps the CoinHistoryDto to a list of CoinPrice objects.
            response.data.map { it.toCoinPrice() }
        }
    }
}