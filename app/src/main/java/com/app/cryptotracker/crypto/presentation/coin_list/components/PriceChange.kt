package com.app.cryptotracker.crypto.presentation.coin_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.cryptotracker.crypto.presentation.models.DisplayableNumber
import com.app.cryptotracker.ui.theme.CryptoTrackerTheme
import com.app.cryptotracker.ui.theme.greenBackground

/**
 * PriceChange is a composable function that displays the price change of a coin.
 *
 * This composable displays the price change as a percentage, with a green background if the price has increased
 * and a red background if the price has decreased.
 *
 * @param change The DisplayableNumber object containing the price change data.
 * @param modifier The modifier to be applied to the root layout of the price change composable.
 */
@Composable
fun PriceChange(
    change: DisplayableNumber,
    modifier: Modifier = Modifier
) {
    // Determines the text color based on the price change value.
    val contentColor =
        if (change.value < 0) MaterialTheme.colorScheme.onErrorContainer else Color.Green

    // Determines the background color based on the price change value.
    val backgroundColor =
        if (change.value < 0) MaterialTheme.colorScheme.errorContainer else greenBackground

    // Row that contains the price change information.
    Row(
        modifier = modifier
            // Clips the row to a rounded rectangle shape.
            .clip(RoundedCornerShape(100f))
            // Sets the background color of the row.
            .background(backgroundColor)
            // Adds horizontal padding to the row.
            .padding(horizontal = 4.dp),
        // Centers the items vertically.
        verticalAlignment = Alignment.CenterVertically

    ) {

        // Displays the arrow icon based on the price change value.
        Icon(
            imageVector = if (change.value > 0) Icons.Default.KeyboardArrowUp else Icons.Default.ArrowDropDown,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = contentColor
        )

        // Displays the price change percentage.
        Text(
            text = "${change.formatted} %",
            color = contentColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

    }

}

/**
 * CoinChangePreview is a composable function that previews the PriceChange composable.
 *
 * This composable is used to preview the PriceChange composable in both light and dark themes.
 */
@PreviewLightDark
@Composable
private fun CoinChangePreview() {
    CryptoTrackerTheme {
        PriceChange(
            change = DisplayableNumber(
                value = 2.43,
                formatted = "2.43"
            ),
        )
    }
}