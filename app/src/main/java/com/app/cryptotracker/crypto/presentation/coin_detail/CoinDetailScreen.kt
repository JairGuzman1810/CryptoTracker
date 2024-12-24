package com.app.cryptotracker.crypto.presentation.coin_detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.cryptotracker.R
import com.app.cryptotracker.crypto.presentation.coin_detail.components.InfoCard
import com.app.cryptotracker.crypto.presentation.coin_list.CoinListState
import com.app.cryptotracker.crypto.presentation.coin_list.components.previewCoin
import com.app.cryptotracker.crypto.presentation.models.toDisplayableNumber
import com.app.cryptotracker.ui.theme.CryptoTrackerTheme
import com.app.cryptotracker.ui.theme.greenBackground

/**
 * CoinDetailScreen is a composable function that displays the details of a selected coin.
 *
 * This screen shows the coin's icon, name, symbol, and various information cards
 * such as market cap, price, and 24-hour change.
 *
 * @param state The CoinListState containing the selected coin and loading status.
 * @param modifier The modifier to apply to the screen.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CoinDetailScreen(
    state: CoinListState,
    modifier: Modifier = Modifier
) {
    // Determines the text color based on the system's dark mode setting.
    val contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black

    // Displays a loading indicator if the data is still loading.
    if (state.isLoading) {
        // Box to center the loading indicator.
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Displays a circular progress indicator.
            CircularProgressIndicator()
        }
    } else if (state.selectedCoin != null) {
        // If a coin is selected, display its details.
        val coin = state.selectedCoin

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Enables vertical scrolling.
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally // Centers content horizontally.
        ) {
            // Displays the coin's icon.
            Icon(
                imageVector = ImageVector.vectorResource(id = coin.iconRes),
                contentDescription = coin.name,
                modifier = Modifier.size(100.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            // Displays the coin's name.
            Text(
                text = coin.name,
                fontSize = 40.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center,
                color = contentColor
            )
            // Displays the coin's symbol.
            Text(
                text = coin.symbol,
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                color = contentColor

            )
            // FlowRow to arrange the InfoCards in a flexible layout.
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center, // Centers the InfoCards horizontally.
            ) {
                // Displays the market cap InfoCard.
                InfoCard(
                    title = stringResource(id = R.string.market_cap),
                    formattedText = "$ ${coin.marketCapUsd.formatted}",
                    icon = ImageVector.vectorResource(id = R.drawable.stock),
                )
                // Displays the price InfoCard.
                InfoCard(
                    title = stringResource(id = R.string.price),
                    formattedText = "$ ${coin.priceUsd.formatted}",
                    icon = ImageVector.vectorResource(id = R.drawable.dollar),
                )

                // Calculates the absolute change in price over 24 hours.
                val absoluteChangeFormatted =
                    (coin.priceUsd.value * (coin.changePercent24Hr.value / 100))
                        .toDisplayableNumber()

                // Determines if the change is positive.
                val isPositive = coin.changePercent24Hr.value > 0

                // Sets the content color for the 24-hour change InfoCard based on whether the change is positive or negative.
                val contentColor24h = if (isPositive) {
                    if (isSystemInDarkTheme()) Color.Green else greenBackground
                } else {
                    MaterialTheme.colorScheme.error
                }
                // Displays the 24-hour change InfoCard.
                InfoCard(
                    title = stringResource(id = R.string.change_last_24h),
                    formattedText = absoluteChangeFormatted.formatted,
                    icon = if (isPositive) ImageVector.vectorResource(id = R.drawable.trending) else ImageVector.vectorResource(id = R.drawable.trending_down),
                    contentColor = contentColor24h
                )
            }

            // Animates the visibility of the LineChart based on whether the coin has price history.
            AnimatedVisibility(
                visible = coin.coinPriceHistory.isNotEmpty() // The chart is visible only if the coin has price history data.
            ) {
                // State to hold the currently selected data point on the chart.
                var selectedDataPoint by remember {
                    mutableStateOf<DataPoint?>(null) // Initially, no data point is selected.
                }
                // State to hold the width of the x-axis labels.
                var labelWidth by remember {
                    mutableFloatStateOf(0f) // Initially, the label width is 0.
                }
                // State to hold the total width of the chart.
                var totalChartWidth by remember {
                    mutableFloatStateOf(0f) // Initially, the total chart width is 0.
                }
                // Calculates the number of data points that can be displayed based on the label width and total chart width.
                val amountOfVisibleDataPoints = if (labelWidth > 0) {
                    ((totalChartWidth - 2.5 * labelWidth) / labelWidth).toInt() // Calculates the number of visible data points.
                } else {
                    0 // If the label width is 0, no data points are visible.
                }
                // Calculates the starting index for the visible data points.
                val startIndex = (coin.coinPriceHistory.lastIndex - amountOfVisibleDataPoints)
                    .coerceAtLeast(0) // Ensures the start index is not negative.
                // Renders the LineChart composable.
                LineChart(
                    dataPoints = coin.coinPriceHistory, // Provides the data points for the chart.
                    style = ChartStyle( // Defines the visual style of the chart.
                        chartLineColor = MaterialTheme.colorScheme.primary, // Sets the color of the chart line.
                        unselectedColor = MaterialTheme.colorScheme.secondary.copy(
                            alpha = 0.3f // Sets the color of unselected elements with transparency.
                        ),
                        selectedColor = MaterialTheme.colorScheme.primary, // Sets the color of selected elements.
                        helperLinesThicknessPx = 5f, // Sets the thickness of helper lines.
                        axisLinesThicknessPx = 5f, // Sets the thickness of axis lines.
                        labelFontSize = 14.sp, // Sets the font size of labels.
                        minYLabelSpacing = 25.dp, // Sets the minimum spacing between y-axis labels.
                        verticalPadding = 8.dp, // Sets the vertical padding around the chart.
                        horizontalPadding = 8.dp, // Sets the horizontal padding around the chart.
                        xAxisLabelSpacing = 8.dp // Sets the spacing between x-axis labels.
                    ),
                    visibleDataPointsIndices = startIndex..coin.coinPriceHistory.lastIndex, // Sets the range of visible data points.
                    unit = "$", // Sets the unit for the y-axis values (e.g., "$").
                    modifier = Modifier
                        .fillMaxWidth() // Makes the chart fill the available width.
                        .aspectRatio(16 / 9f) // Sets the aspect ratio of the chart to 16:9.
                        .onSizeChanged { totalChartWidth = it.width.toFloat() }, // Updates the total chart width when the size changes.
                    selectedDataPoint = selectedDataPoint, // Provides the currently selected data point.
                    onSelectedDataPoint = {
                        selectedDataPoint = it // Callback to update the selected data point.
                    },
                    onXLabelWidthChange = { labelWidth = it } // Callback to update the label width.
                )
            }
        }
    }
}

/**
 * CoinDetailScreenPreview is a preview function for the CoinDetailScreen.
 *
 * This function allows you to preview the CoinDetailScreen in the IDE.
 */
@PreviewLightDark
@Composable
private fun CoinDetailScreenPreview() {
    CryptoTrackerTheme {
        CoinDetailScreen(
            state = CoinListState(
                selectedCoin = previewCoin
            ),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        )
    }
}