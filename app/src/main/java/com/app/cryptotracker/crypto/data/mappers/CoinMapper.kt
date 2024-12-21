package com.app.cryptotracker.crypto.data.mappers

import com.app.cryptotracker.crypto.data.networking.dto.CoinDto
import com.app.cryptotracker.crypto.domain.Coin

/**
 * toCoin maps a CoinDto to a Coin domain object.
 *
 * This function converts a CoinDto object, which represents a coin from the API response,
 * to a Coin object, which is the domain representation of a coin.
 *
 * @receiver The CoinDto object to be mapped.
 * @return The corresponding Coin domain object.
 */
fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        marketCapUsd = marketCapUsd,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr
    )
}