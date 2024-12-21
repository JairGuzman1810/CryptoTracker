package com.app.cryptotracker.crypto.presentation.coin_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.cryptotracker.crypto.domain.Coin
import com.app.cryptotracker.crypto.presentation.models.CoinUi
import com.app.cryptotracker.crypto.presentation.models.toCoinUi
import com.app.cryptotracker.ui.theme.CryptoTrackerTheme

/**
 * CoinListItem is a composable function that displays a single coin's information in a list.
 *
 * This composable displays the coin's icon, symbol, name, price, and 24-hour price change.
 * It is designed to be used within a list of coins, such as in a RecyclerView or LazyColumn.
 *
 * @param coin The CoinUi object containing the data to display.
 * @param onClick The callback to be invoked when the coin item is clicked.
 * @param modifier The modifier to be applied to the root layout of the coin item.
 */
@Composable
fun CoinListItem(
    coin: CoinUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Determines the text color based on the system's dark mode setting.
    val contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black

    // Row that contains the coin information.
    Row(
        modifier = modifier
            // Makes the entire row clickable.
            .clickable { onClick() }
            // Adds padding around the row.
            .padding(16.dp),
        // Centers the items vertically.
        verticalAlignment = Alignment.CenterVertically,
        // Adds space between the items horizontally.
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Displays the coin's icon.
        Icon(
            imageVector = ImageVector.vectorResource(id = coin.iconRes),
            contentDescription = coin.name,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(85.dp)
        )

        // Column for the coin's symbol and name.
        Column(
            modifier = Modifier.weight(1f),
        ) {
            // Displays the coin's symbol.
            Text(
                text = coin.symbol,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
            // Displays the coin's name.
            Text(
                text = coin.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                color = contentColor
            )
        }

        // Column for the coin's price and price change.
        Column(
            horizontalAlignment = Alignment.End
        ) {
            // Displays the coin's price.
            Text(
                text = "$ ${coin.priceUsd.formatted}",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = contentColor
            )
            // Adds a vertical space.
            Spacer(modifier = Modifier.height(8.dp))
            // Displays the coin's price change.
            PriceChange(
                change = coin.changePercent24Hr,
            )
        }
    }
}

/**
 * CoinListItemPreview is a composable function that previews the CoinListItem.
 *
 * This composable is used to preview the CoinListItem in both light and dark themes.
 */
@PreviewLightDark
@Composable
private fun CoinListItemPreview() {
    CryptoTrackerTheme {
        CoinListItem(
            coin = previewCoin,
            onClick = {},
            modifier = Modifier.background(
                MaterialTheme.colorScheme.background
            )
        )
    }
}

/**
 * previewCoin is a sample Coin object used for previewing the CoinListItem.
 *
 * This object is used to display a sample coin in the preview.
 */
internal val previewCoin = Coin(
    id = "bitcoin",
    rank = 1,
    name = "Bitcoin",
    symbol = "BTC",
    marketCapUsd = 123456789.0,
    priceUsd = 12345.67,
    changePercent24Hr = -0.1,
).toCoinUi()