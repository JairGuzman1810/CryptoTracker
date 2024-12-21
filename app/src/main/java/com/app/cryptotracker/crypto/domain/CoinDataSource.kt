package com.app.cryptotracker.crypto.domain

import com.app.cryptotracker.core.domain.util.NetworkError
import com.app.cryptotracker.core.domain.util.Result

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
}