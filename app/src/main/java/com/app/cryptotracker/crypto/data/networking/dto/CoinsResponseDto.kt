package com.app.cryptotracker.crypto.data.networking.dto

import kotlinx.serialization.Serializable

/**
 * CoinsResponseDto is a data transfer object representing the API response for a list of coins.
 *
 * This data class is used to deserialize the JSON response from the API into a structured object.
 * It contains a list of CoinDto objects, which represent the individual coins returned by the API.
 *
 * @property data The list of CoinDto objects.
 */
@Serializable
data class CoinsResponseDto(
    val data: List<CoinDto>
)