package com.app.cryptotracker.crypto.presentation.models

import androidx.annotation.DrawableRes
import com.app.cryptotracker.crypto.domain.Coin
import com.app.cryptotracker.util.getDrawableIdForCoin
import java.text.NumberFormat
import java.util.Locale

/**
 * CoinUi represents the UI-ready data for a cryptocurrency.
 *
 * This class is used to format and prepare cryptocurrency data for display in the UI.
 * It includes formatted values for market cap, price, and change percentage,
 * as well as a drawable resource ID for the coin's icon.
 *
 * @property id The unique identifier of the coin.
 * @property rank The rank of the coin by market capitalization.
 * @property name The name of the coin (e.g., Bitcoin).
 * @property symbol The symbol of the coin (e.g., BTC).
 * @property marketCapUsd The market capitalization of the coin in USD, formatted for display.
 * @property priceUsd The current price of the coin in USD, formatted for display.
 * @property changePercent24Hr The percentage change in the coin's price over the last 24 hours, formatted for display.
 * @property iconRes The drawable resource ID for the coin's icon.
 */
data class CoinUi(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: DisplayableNumber,
    val priceUsd: DisplayableNumber,
    val changePercent24Hr: DisplayableNumber,
    @DrawableRes val iconRes: Int,
)

/**
 * DisplayableNumber represents a number formatted for display in the UI.
 *
 * This class holds both the raw numeric value and its formatted string representation.
 *
 * @property value The raw numeric value.
 * @property formatted The formatted string representation of the value.
 */
data class DisplayableNumber(
    val value: Double,
    val formatted: String,
)

/**
 * Converts a Coin domain object to a CoinUi object for UI presentation.
 *
 * This extension function takes a Coin object and transforms it into a CoinUi object,
 * formatting the numeric values and retrieving the appropriate icon resource.
 *
 * @receiver The Coin object to be converted.
 * @return A CoinUi object with formatted data and the icon resource.
 */
fun Coin.toUi(): CoinUi {
    // Creates and returns a CoinUi object with the formatted data.
    return CoinUi(
        id = id,
        name = name,
        symbol = symbol,
        rank = rank,
        // Formats the priceUsd value for display.
        priceUsd = priceUsd.toDisplayableNumber(),
        // Formats the marketCapUsd value for display.
        marketCapUsd = marketCapUsd.toDisplayableNumber(),
        // Formats the changePercent24Hr value for display.
        changePercent24Hr = changePercent24Hr.toDisplayableNumber(),
        // Retrieves the drawable resource ID for the coin's icon.
        iconRes = getDrawableIdForCoin(symbol)
    )
}

/**
 * Converts a Double to a DisplayableNumber for UI presentation.
 *
 * This extension function takes a Double and formats it as a string with a specific number of decimal places.
 *
 * @receiver The Double to be formatted.
 * @return A DisplayableNumber object with the formatted value.
 */
fun Double.toDisplayableNumber(): DisplayableNumber {
    // Creates a NumberFormat instance with the default locale.
    val formatted = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        // Sets the minimum number of fraction digits to 2.
        minimumFractionDigits = 2
        // Sets the maximum number of fraction digits to 2.
        maximumFractionDigits = 2
    }

    // Creates and returns a DisplayableNumber object with the formatted value.
    return DisplayableNumber(
        value = this,
        formatted = formatted.format(this)
    )
}
