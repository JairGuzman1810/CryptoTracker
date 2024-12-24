package com.app.cryptotracker.crypto.presentation.coin_detail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.cryptotracker.crypto.domain.CoinPrice
import com.app.cryptotracker.ui.theme.CryptoTrackerTheme
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt
import kotlin.random.Random


/**
 * Composable function that renders a line chart.
 *
 * This function draws a line chart based on the provided data points, style, and other parameters.
 * It supports interactive features like selecting a data point by dragging horizontally.
 *
 * @param dataPoints The list of DataPoint objects representing the data to be plotted.
 * @param style The ChartStyle object defining the visual appearance of the chart.
 * @param visibleDataPointsIndices The range of indices representing the data points currently visible on the chart.
 * @param unit The unit of measurement for the y-axis values (e.g., "$", "BTC").
 * @param modifier The Modifier to be applied to the chart.
 * @param selectedDataPoint The currently selected DataPoint, or null if none is selected.
 * @param onSelectedDataPoint Callback function invoked when a data point is selected.
 * @param onXLabelWidthChange Callback function invoked when the width of the x-axis labels changes.
 * @param showHelperLines Boolean indicating whether to display helper lines.
 */
@Composable
fun LineChart(
    dataPoints: List<DataPoint>,
    style: ChartStyle,
    visibleDataPointsIndices: IntRange,
    unit: String,
    modifier: Modifier = Modifier,
    selectedDataPoint: DataPoint? = null,
    onSelectedDataPoint: (DataPoint) -> Unit = {},
    onXLabelWidthChange: (Float) -> Unit = {},
    showHelperLines: Boolean = true
) {
    // Retrieves the current text style from the composition local and applies the label font size.
    val textStyle = LocalTextStyle.current.copy(
        fontSize = style.labelFontSize
    )

    // Filters the data points to only include those within the visible range.
    val visibleDataPoints = remember(dataPoints, visibleDataPointsIndices) {
        dataPoints.slice(visibleDataPointsIndices)
    }

    // Calculates the maximum y-value among the visible data points.
    val maxYValue = remember(visibleDataPoints) {
        visibleDataPoints.maxOfOrNull { it.y } ?: 0f
    }

    // Calculates the minimum y-value among the visible data points.
    val minYValue = remember(visibleDataPoints) {
        visibleDataPoints.minOfOrNull { it.y } ?: 0f
    }

    // Creates a TextMeasurer to measure text dimensions.
    val measurer = rememberTextMeasurer()

    // State variable to store the width of the x-axis labels.
    var xLabelWidth by remember {
        mutableFloatStateOf(0f)
    }
    // LaunchedEffect to invoke the callback when the x-axis label width changes.
    LaunchedEffect(key1 = xLabelWidth) {
        onXLabelWidthChange(xLabelWidth)
    }

    // Remembers the index of the selected data point.
    val selectedDataPointIndex = remember(selectedDataPoint) {
        dataPoints.indexOf(selectedDataPoint)
    }
    // State variable to store the list of data points with their drawn positions.
    var drawPoints by remember {
        mutableStateOf(listOf<DataPoint>())
    }
    // State variable to track whether data points are being shown.
    var isShowingDataPoints by remember {
        mutableStateOf(selectedDataPoint != null)
    }

    // Canvas composable to draw the chart.
    Canvas(modifier = modifier
        .fillMaxSize()
        // Adds pointer input to the canvas to detect touch events.
        .pointerInput(drawPoints, xLabelWidth) {
            // Detects horizontal drag gestures on the canvas.
            detectHorizontalDragGestures { change, _ ->
                // Calculates the index of the data point under the drag position.
                val newSelectedDataPointIndex = getSelectedDataPointIndex(
                    touchOffsetX = change.position.x,
                    triggerWidth = xLabelWidth,
                    drawPoints = drawPoints
                )
                // Checks if the new selected index is within the visible range.
                isShowingDataPoints =
                    (newSelectedDataPointIndex + visibleDataPointsIndices.first) in visibleDataPointsIndices
                // If a data point is selected and within the visible range, invoke the callback.
                if (isShowingDataPoints) {
                    onSelectedDataPoint(dataPoints[newSelectedDataPointIndex])
                }
            }
        }) {
        // Converts Dp values to pixels for various spacing and padding values.
        val minLabelSpacingYPx = style.minYLabelSpacing.toPx()
        val verticalPaddingPx = style.verticalPadding.toPx()
        val horizontalPaddingPx = style.horizontalPadding.toPx()
        val xAxisLabelSpacingPx = style.xAxisLabelSpacing.toPx()

        // Measures the x-axis labels and calculates the maximum width, height, and line count.
        val xLabelTextLayoutResults = visibleDataPoints.map {
            measurer.measure(
                text = it.xLabel, style = textStyle.copy(textAlign = TextAlign.Center)
            )
        }
        val maxXLabelWidth = xLabelTextLayoutResults.maxOfOrNull { it.size.width } ?: 0
        val maxXLabelHeight = xLabelTextLayoutResults.maxOfOrNull { it.size.height } ?: 0
        val maxXLabelLineCount = xLabelTextLayoutResults.maxOfOrNull { it.lineCount } ?: 0
        val xLabelLineHeight =
            if (maxXLabelLineCount > 0) maxXLabelHeight / maxXLabelLineCount else 0

        // Calculates the height of the viewport for the chart.
        val viewPortHeightPx =
            size.height - (maxXLabelHeight + 2 * verticalPaddingPx + xLabelLineHeight + xAxisLabelSpacingPx)

        // Y-LABEL CALCULATION
        // Calculates the height of the viewport for the labels.
        val labelViewPortHeightPx = viewPortHeightPx + xLabelLineHeight
        // Calculates the number of labels to display on the y-axis.
        val labelCountExcludingLastLabel =
            ((labelViewPortHeightPx / (xLabelLineHeight + minLabelSpacingYPx))).toInt()

        // Calculates the value increment between each y-axis label.
        val valueIncrement = (maxYValue - minYValue) / labelCountExcludingLastLabel

        // Creates a list of ValueLabel objects for the y-axis labels.
        val yLabels = (0..labelCountExcludingLastLabel).map {
            ValueLabel(
                value = maxYValue - (valueIncrement * it), unit = unit
            )
        }

        // Measures the y-axis labels and calculates the maximum width.
        val yLabelTextLayoutResults = yLabels.map {
            measurer.measure(
                text = it.formatted(), style = textStyle
            )
        }
        val maxYLabelWidth = yLabelTextLayoutResults.maxOfOrNull { it.size.width } ?: 0

        // Calculates the coordinates of the viewport.
        val viewPortTopY = verticalPaddingPx + xLabelLineHeight + 10f
        val viewPortRightX = size.width
        val viewPortBottomY = viewPortTopY + viewPortHeightPx
        val viewPortLeftX = 2f * horizontalPaddingPx + maxYLabelWidth

        // Updates the x-axis label width.
        xLabelWidth = maxXLabelWidth + xAxisLabelSpacingPx
        // Iterates through each x-axis label and draws it on the canvas.
        xLabelTextLayoutResults.forEachIndexed { index, result ->
            // Calculates the x position for the current label.
            val x = viewPortLeftX + xAxisLabelSpacingPx / 2f + xLabelWidth * index
            // Draws the x-axis label text.
            drawText(
                textLayoutResult = result, topLeft = Offset(
                    x = x, y = viewPortBottomY + xAxisLabelSpacingPx
                ),
                // Sets the color of the label based on whether it's the selected data point.
                color = if (index == selectedDataPointIndex) {
                    style.selectedColor
                } else style.unselectedColor
            )

            // Checks if helper lines should be drawn.
            if (showHelperLines) {
                // Draws a vertical line (helper line) for the current x-axis label.
                drawLine(
                    // Sets the color of the helper line based on whether it's the selected data point.
                    color = if (selectedDataPointIndex == index) {
                        style.selectedColor
                    } else style.unselectedColor,
                    // Sets the starting point of the line.
                    start = Offset(
                        x = x + result.size.width / 2f, y = viewPortBottomY
                    ),
                    // Sets the ending point of the line.
                    end = Offset(
                        x = x + result.size.width / 2f, y = viewPortTopY
                    ),
                    // Sets the thickness of the line, making it thicker if it's the selected data point.
                    strokeWidth = if (selectedDataPointIndex == index) {
                        style.helperLinesThicknessPx * 1.8f
                    } else style.helperLinesThicknessPx
                )
            }

            // Checks if the current label is the selected data point.
            if (selectedDataPointIndex == index) {
                // Creates a ValueLabel object to display the y-value of the selected data point.
                val valueLabel = ValueLabel(
                    value = visibleDataPoints[index].y, unit = unit
                )
                // Measures the text layout of the value label.
                val valueResult = measurer.measure(
                    text = valueLabel.formatted(), style = textStyle.copy(
                        color = style.selectedColor
                    ), maxLines = 1
                )
                // Calculates the x position for the value label text, adjusting it if it's the last data point.
                val textPositionX = if (selectedDataPointIndex == visibleDataPointsIndices.last) {
                    x - valueResult.size.width
                } else {
                    x - valueResult.size.width / 2f
                } + result.size.width / 2f
                // Checks if the value label text is within the visible range of the canvas.
                val isTextInVisibleRange =
                    (size.width - textPositionX).roundToInt() in 0..size.width.roundToInt()
                // If the text is within the visible range, draw it.
                if (isTextInVisibleRange) {
                    drawText(
                        textLayoutResult = valueResult, topLeft = Offset(
                            x = textPositionX, y = viewPortTopY - valueResult.size.height - 10f
                        )
                    )
                }
            }
        }

        // Calculates the total height required for all x-axis labels.
        val heightRequiredForLabels = xLabelLineHeight * (labelCountExcludingLastLabel + 1)
        // Calculates the remaining height available for spacing between labels.
        val remainingHeightForLabels = labelViewPortHeightPx - heightRequiredForLabels
        // Calculates the space to be placed between each y-axis label.
        val spaceBetweenLabels = remainingHeightForLabels / labelCountExcludingLastLabel

        // Iterates through each y-axis label and draws it on the canvas.
        yLabelTextLayoutResults.forEachIndexed { index, result ->
            // Calculates the x position for the current y-axis label.
            val x = horizontalPaddingPx + maxYLabelWidth - result.size.width.toFloat()
            // Calculates the y position for the current y-axis label.
            val y =
                viewPortTopY + index * (xLabelLineHeight + spaceBetweenLabels) - xLabelLineHeight / 2f
            // Draws the y-axis label text.
            drawText(
                textLayoutResult = result, topLeft = Offset(
                    x = x, y = y
                ), color = style.unselectedColor
            )

            // Checks if helper lines should be drawn.
            if (showHelperLines) {
                // Draws a horizontal line (helper line) for the current y-axis label.
                drawLine(
                    color = style.unselectedColor,
                    // Sets the starting point of the line.
                    start = Offset(
                        x = viewPortLeftX, y = y + result.size.height.toFloat() / 2f
                    ),
                    // Sets the ending point of the line.
                    end = Offset(
                        x = viewPortRightX, y = y + result.size.height.toFloat() / 2f
                    ), strokeWidth = style.helperLinesThicknessPx
                )
            }
        }

        // Calculates the actual points to be drawn on the chart based on the visible data points.
        drawPoints = visibleDataPointsIndices.map {
            // Calculates the x-coordinate for the current data point.
            val x =
                viewPortLeftX + (it - visibleDataPointsIndices.first) * xLabelWidth + xLabelWidth / 2f
            // [minYValue; maxYValue] -> [0; 1] (Comment explaining the normalization range)
            // Normalizes the y-value to a ratio between 0 and 1 based on the min and max y-values.
            val ratio = (dataPoints[it].y - minYValue) / (maxYValue - minYValue)
            // Calculates the y-coordinate for the current data point based on the normalized ratio.
            val y = viewPortBottomY - (ratio * viewPortHeightPx)
            // Creates a DataPoint object with the calculated x and y coordinates and the x-axis label.
            DataPoint(
                x = x, y = y, xLabel = dataPoints[it].xLabel
            )
        }

        // Creates two mutable lists to store control points for the cubic Bezier curve.
        val conPoints1 = mutableListOf<DataPoint>()
        val conPoints2 = mutableListOf<DataPoint>()
        // Iterates through the drawPoints list to calculate control points for the curve.
        for (i in 1 until drawPoints.size) {
            // Gets the previous and current data points.
            val p0 = drawPoints[i - 1]
            val p1 = drawPoints[i]

            // Calculates the x-coordinate of the control points (midpoint between two data points).
            val x = (p1.x + p0.x) / 2f
            // Sets the y-coordinate of the first control point to the y-coordinate of the previous data point.
            val y1 = p0.y
            // Sets the y-coordinate of the second control point to the y-coordinate of the current data point.
            val y2 = p1.y

            // Adds the calculated control points to their respective lists.
            conPoints1.add(DataPoint(x, y1, ""))
            conPoints2.add(DataPoint(x, y2, ""))
        }

        // Creates a Path object to draw the line chart.
        val linePath = Path().apply {
            // Checks if there are any data points to draw.
            if (drawPoints.isNotEmpty()) {
                // Moves the starting point of the path to the first data point.
                moveTo(drawPoints.first().x, drawPoints.first().y)

                // Iterates through the data points to draw the cubic Bezier curve.
                for (i in 1 until drawPoints.size) {
                    // Draws a cubic Bezier curve using the calculated control points and data points.
                    cubicTo(
                        x1 = conPoints1[i - 1].x,
                        y1 = conPoints1[i - 1].y,
                        x2 = conPoints2[i - 1].x,
                        y2 = conPoints2[i - 1].y,
                        x3 = drawPoints[i].x,
                        y3 = drawPoints[i].y
                    )
                }
            }
        }
        // Draws the line path on the canvas.
        drawPath(
            path = linePath, color = style.chartLineColor, style = Stroke(
                width = 5f, cap = StrokeCap.Round // Sets the line cap to round for smoother edges.
            )
        )

        // Iterates through each data point to draw circles.
        drawPoints.forEachIndexed { index, point ->
            // Checks if data point indicators should be shown.
            if (isShowingDataPoints) {
                // Calculates the offset for the circle.
                val circleOffset = Offset(
                    x = point.x, y = point.y
                )
                // Draws a circle at the current data point.
                drawCircle(
                    color = style.selectedColor, radius = 10f, center = circleOffset
                )

                // Checks if the current data point is the selected one.
                if (selectedDataPointIndex == index) {
                    // Draws a white circle behind the selected data point.
                    drawCircle(
                        color = Color.White, radius = 15f, center = circleOffset
                    )
                    // Draws an outlined circle around the selected data point.
                    drawCircle(
                        color = style.selectedColor,
                        radius = 15f,
                        center = circleOffset,
                        style = Stroke(
                            width = 3f
                        )
                    )
                }
            }
        }
    }
}

/**
 * getSelectedDataPointIndex Determines the index of the data point that has been selected
 * based on a touch event's x-coordinate.
 *
 * @param touchOffsetX The x-coordinate of the touch event on the canvas.
 * @param triggerWidth The width of the area around a data point that triggers its selection.
 * @param drawPoints The list of DataPoint objects representing the points drawn on the chart.
 * @return The index of the selected data point in the drawPoints list, or -1 if no point is selected.
 */
private fun getSelectedDataPointIndex(
    touchOffsetX: Float, triggerWidth: Float, drawPoints: List<DataPoint>
): Int {
    // Calculates the left boundary of the trigger range around the touch point.
    val triggerRangeLeft = touchOffsetX - triggerWidth / 2f
    // Calculates the right boundary of the trigger range around the touch point.
    val triggerRangeRight = touchOffsetX + triggerWidth / 2f
    // Returns the index of the first data point whose x-coordinate falls within the trigger range.
    // If no data point is found within the range, it returns -1.
    return drawPoints.indexOfFirst {
        it.x in triggerRangeLeft..triggerRangeRight
    }
}

/**
 * This is a preview function for the LineChart composable.
 * It allows you to see how the LineChart will look in the IDE's preview window.
 */
@Preview(widthDp = 1000) // Sets the width of the preview to 1000 density-independent pixels.
@Composable
private fun LineChartPreview() {
    CryptoTrackerTheme { // Applies the CryptoTrackerTheme to the preview.
        // Creates a list of randomized CoinPrice objects for demonstration purposes.
        val coinHistoryRandomized = remember {
            (1..20).map {
                CoinPrice(
                    priceUsd = Random.nextFloat() * 1000.0, // Generates a random price between 0 and 1000.
                    dateTime = ZonedDateTime.now()
                        .plusHours(it.toLong()) // Creates a date/time object, adding hours to make it unique.
                )
            }
        }
        // Defines the visual style of the chart.
        val style = ChartStyle(
            chartLineColor = Color.Black, // Sets the color of the chart line.
            unselectedColor = Color(0xFF7C7C7C), // Sets the color of unselected elements.
            selectedColor = Color.Black, // Sets the color of selected elements.
            helperLinesThicknessPx = 1f, // Sets the thickness of helper lines.
            axisLinesThicknessPx = 5f, // Sets the thickness of axis lines.
            labelFontSize = 14.sp, // Sets the font size of labels.
            minYLabelSpacing = 25.dp, // Sets the minimum spacing between y-axis labels.
            verticalPadding = 8.dp, // Sets the vertical padding around the chart.
            horizontalPadding = 8.dp, // Sets the horizontal padding around the chart.
            xAxisLabelSpacing = 8.dp // Sets the spacing between x-axis labels.
        )
        // Transforms the CoinPrice objects into DataPoint objects for the chart.
        val dataPoints = remember {
            coinHistoryRandomized.map {
                DataPoint(
                    x = it.dateTime.hour.toFloat(), // Uses the hour of the date/time as the x-coordinate.
                    y = it.priceUsd.toFloat(), // Uses the price as the y-coordinate.
                    xLabel = DateTimeFormatter.ofPattern("ha\nM/d") // Defines the format for the x-axis labels (hour, AM/PM, month/day).
                        .format(it.dateTime) // Formats the date/time into a string for the x-axis label.
                )
            }
        }
        // Renders the LineChart composable with the defined data and style.
        LineChart(
            dataPoints = dataPoints, // Provides the data points to be plotted.
            style = style, // Provides the visual style for the chart.
            visibleDataPointsIndices = 0..19, // Sets the range of visible data points (first 20).
            unit = "$", // Sets the unit for the y-axis values (e.g., "$").
            modifier = Modifier
                .width(700.dp) // Sets the width of the chart.
                .height(300.dp) // Sets the height of the chart.
                .background(Color.White), // Sets the background color of the chart.
            selectedDataPoint = dataPoints[1] // Sets the second data point as the initially selected one.
        )
    }
}