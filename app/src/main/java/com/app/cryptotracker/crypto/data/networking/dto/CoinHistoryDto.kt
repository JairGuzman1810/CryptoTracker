package com.app.cryptotracker.crypto.data.networking.dto

import kotlinx.serialization.Serializable

/**
 * CoinHistoryDto is a data transfer object (DTO) representing the API response for the historical price data of a coin.
 *
 * This data class is used to deserialize the JSON response from the API when requesting the price history of a specific coin.
 * It contains a list of CoinPriceDto objects, where each object represents the price of the coin at a specific point in time.
 *
 * @property data The list of CoinPriceDto objects, each representing a historical price point for the coin.
 */
@Serializable
data class CoinHistoryDto(
    val data: List<CoinPriceDto>
)