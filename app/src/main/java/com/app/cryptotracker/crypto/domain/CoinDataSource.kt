package com.app.cryptotracker.crypto.domain

import com.app.cryptotracker.core.domain.util.NetworkError
import com.app.cryptotracker.core.domain.util.Result
import java.time.ZonedDateTime

/**
 * CoinDataSource is an interface for fetching coin data.
 *
 * This interface defines the contract for any data source that provides coin information.
 * It abstracts the underlying implementation of how the data is fetched,
 * allowing for different data sources (e.g., remote API, local database) to be used interchangeably.
 */
interface CoinDataSource {
    /**
     * getCoins fetches a list of coins.
     *
     * This suspend function retrieves a list of Coin objects.
     * It returns a Result object, which can either be a Success containing the list of coins
     * or an Error containing a NetworkError if something went wrong.
     *
     * @return A Result object containing either a list of Coin objects or a NetworkError.
     */
    suspend fun getCoins(): Result<List<Coin>, NetworkError>

    /**
     * getCoinHistory fetches the price history of a specific coin within a given time range.
     *
     * This suspend function retrieves a list of CoinPrice objects representing the price history of a coin.
     * It returns a Result object, which can either be a Success containing the list of CoinPrice objects
     * or an Error containing a NetworkError if something went wrong.
     *
     * @param coinId The ID of the coin for which to fetch the price history.
     * @param start The start of the time range for which to fetch the price history.
     * @param end The end of the time range for which to fetch the price history.
     * @return A Result object containing either a list of CoinPrice objects or a NetworkError.
     */
    suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError>
}