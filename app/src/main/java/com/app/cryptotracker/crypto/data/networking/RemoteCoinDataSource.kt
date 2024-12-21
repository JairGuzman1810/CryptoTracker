package com.app.cryptotracker.crypto.data.networking

import com.app.cryptotracker.core.data.networking.constructUrl
import com.app.cryptotracker.core.data.networking.safeCall
import com.app.cryptotracker.core.domain.util.NetworkError
import com.app.cryptotracker.core.domain.util.Result
import com.app.cryptotracker.core.domain.util.map
import com.app.cryptotracker.crypto.data.mappers.toCoin
import com.app.cryptotracker.crypto.data.networking.dto.CoinResponseDto
import com.app.cryptotracker.crypto.domain.Coin
import com.app.cryptotracker.crypto.domain.CoinDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

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
        return safeCall<CoinResponseDto> {
            // Makes a GET request to the /assets endpoint.
            httpClient.get(
                urlString = constructUrl("/assets")
            )
        }.map { response ->
            // Maps the CoinResponseDto to a list of Coin objects.
            response.data.map { it.toCoin() }
        }
    }
}