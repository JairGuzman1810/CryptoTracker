package com.app.cryptotracker.crypto.data.networking.dto

import kotlinx.serialization.Serializable

/**
 * CoinDto is a data transfer object representing a coin from the API response.
 *
 * This data class is used to deserialize the JSON response from the API into a structured object.
 * It contains the raw data received from the API for a single coin.
 *
 * @property id The unique identifier of the coin.
 * @property rank The rank of the coin by market capitalization.
 * @property name The name of the coin (e.g., Bitcoin).
 * @property symbol The symbol of the coin (e.g., BTC).
 * @property marketCapUsd The market capitalization of the coin in USD.
 * @property priceUsd The current price of the coin in USD.
 * @property changePercent24Hr The percentage change in the coin's price over the last 24 hours.
 */
@Serializable
data class CoinDto(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: Double,
    val priceUsd: Double,
    val changePercent24Hr: Double,
)