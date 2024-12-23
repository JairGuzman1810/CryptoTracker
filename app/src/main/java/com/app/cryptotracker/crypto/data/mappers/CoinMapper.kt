package com.app.cryptotracker.crypto.data.mappers

import com.app.cryptotracker.crypto.data.networking.dto.CoinDto
import com.app.cryptotracker.crypto.data.networking.dto.CoinPriceDto
import com.app.cryptotracker.crypto.domain.Coin
import com.app.cryptotracker.crypto.domain.CoinPrice
import java.time.Instant
import java.time.ZoneId

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

/**
 * toCoinPrice maps a CoinPriceDto to a CoinPrice domain object.
 *
 * This function converts a CoinPriceDto object, which represents a coin's price at a specific time from the API response,
 * to a CoinPrice object, which is the domain representation of a coin's price.
 *
 * @receiver The CoinPriceDto object to be mapped.
 * @return The corresponding CoinPrice domain object.
 */
fun CoinPriceDto.toCoinPrice(): CoinPrice {
    return CoinPrice(
        priceUsd = priceUsd,
        // Converts the timestamp (in seconds) to an Instant and then to a ZonedDateTime in UTC.
        dateTime = Instant.ofEpochSecond(time)
            .atZone(ZoneId.of("UTC"))
    )
}