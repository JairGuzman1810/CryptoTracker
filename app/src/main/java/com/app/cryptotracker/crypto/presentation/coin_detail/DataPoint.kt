package com.app.cryptotracker.crypto.presentation.coin_detail

/**
 * DataPoint represents a single point in a chart.
 *
 * This data class encapsulates the x and y coordinates of a point, along with
 * a label for the x-axis. It's used to represent individual data points in a
 * line chart or similar visualizations.
 *
 * @property x The x-coordinate of the data point.
 * @property y The y-coordinate of the data point.
 * @property xLabel The label to be displayed on the x-axis for this data point.
 */
data class DataPoint(
    val x: Float,
    val y: Float,
    val xLabel: String
)