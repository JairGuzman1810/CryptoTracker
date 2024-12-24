package com.app.cryptotracker.crypto.presentation.coin_detail

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

/**
 * ChartStyle defines the visual style of a chart.
 *
 * This data class encapsulates various styling properties for a chart, such as colors,
 * line thicknesses, font sizes, and padding. It allows for consistent and customizable
 * styling of the chart's visual elements.
 *
 * @property chartLineColor The color of the main chart line.
 * @property unselectedColor The color used for unselected chart elements (e.g., data points).
 * @property selectedColor The color used for selected chart elements (e.g., a highlighted data point).
 * @property helperLinesThicknessPx The thickness of the helper lines in pixels.
 * @property axisLinesThicknessPx The thickness of the axis lines in pixels.
 * @property labelFontSize The font size for the chart labels.
 * @property minYLabelSpacing The minimum vertical spacing between y-axis labels.
 * @property verticalPadding The vertical padding around the chart.
 * @property horizontalPadding The horizontal padding around the chart.
 * @property xAxisLabelSpacing The spacing between x-axis labels.
 */
data class ChartStyle(
    val chartLineColor: Color,
    val unselectedColor: Color,
    val selectedColor: Color,
    val helperLinesThicknessPx: Float,
    val axisLinesThicknessPx: Float,
    val labelFontSize: TextUnit,
    val minYLabelSpacing: Dp,
    val verticalPadding: Dp,
    val horizontalPadding: Dp,
    val xAxisLabelSpacing: Dp,
)