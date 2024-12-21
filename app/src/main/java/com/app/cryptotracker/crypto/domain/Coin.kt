package com.app.cryptotracker.crypto.domain

/**
 * Coin represents a cryptocurrency in the domain layer.
 *
 * This data class holds the core information about a cryptocurrency,
 * including its ID, rank, name, symbol, market capitalization, price, and 24-hour change percentage.
 *
 * @property id The unique identifier of the coin.
 * @property rank The rank of the coin by market capitalization.
 * @property name The name of the coin (e.g., Bitcoin).
 * @property symbol The symbol of the coin (e.g., BTC).
 * @property marketCapUsd The market capitalization of the coin in USD.
 * @property priceUsd The current price of the coin in USD.
 * @property changePercent24Hr The percentage change in the coin's price over the last 24 hours.
 */
data class Coin(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: Double,
    val priceUsd: Double,
    val changePercent24Hr: Double,
)