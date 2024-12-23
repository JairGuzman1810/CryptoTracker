package com.app.cryptotracker.crypto.domain

import java.time.ZonedDateTime

/**
 * CoinPrice represents the price of a coin at a specific point in time.
 *
 * This data class holds the price of a coin in USD and the corresponding date and time.
 * It's used to represent historical price data for a coin.
 *
 * @param priceUsd The price of the coin in USD.
 * @param dateTime The date and time at which the price was recorded.
 */
data class CoinPrice(
    val priceUsd: Double,
    val dateTime: ZonedDateTime
)
