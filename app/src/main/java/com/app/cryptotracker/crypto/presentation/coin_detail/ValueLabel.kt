package com.app.cryptotracker.crypto.presentation.coin_detail

import android.icu.text.NumberFormat
import java.util.Locale

/**
 * ValueLabel represents a formatted label for a value, typically used on a chart axis.
 *
 * This data class encapsulates a numerical value and its associated unit (e.g., "$", "BTC").
 * It provides a method to format the value according to its magnitude, ensuring
 * appropriate decimal precision.
 *
 * @property value The numerical value to be displayed.
 * @property unit The unit associated with the value (e.g., "$", "BTC", "%").
 */
data class ValueLabel(
    val value: Float,
    val unit: String
) {
    /**
     * Formats the value with appropriate decimal precision based on its magnitude.
     *
     * The formatting logic adjusts the number of decimal places as follows:
     * - If the value is greater than 1000, it's formatted with 0 decimal places.
     * - If the value is between 2 and 999 (inclusive), it's formatted with 2 decimal places.
     * - Otherwise, it's formatted with 3 decimal places.
     *
     * @return A formatted string representation of the value, including the unit.
     */
    fun formatted(): String {
        val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
            val fractionDigits = when {
                value > 1000 -> 0
                value in 2f..999f -> 2
                else -> 3
            }
            maximumFractionDigits = fractionDigits
            minimumFractionDigits = 0
        }
        return "${formatter.format(value)}$unit"
    }
}