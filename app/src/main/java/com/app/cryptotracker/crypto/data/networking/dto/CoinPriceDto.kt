package com.app.cryptotracker.crypto.data.networking.dto

import kotlinx.serialization.Serializable

/**
 * CoinPriceDto is a data transfer object (DTO) representing the price of a coin at a specific point in time.
 *
 * This DTO is used for receiving coin price data from the network.
 * It's designed to match the structure of the JSON response from the API.
 *
 * @param priceUsd The price of the coin in USD.
 * @param time The timestamp (in milliseconds since the epoch) at which the price was recorded.
 */
@Serializable
data class CoinPriceDto(
    val priceUsd: Double,
    val time: Long
)