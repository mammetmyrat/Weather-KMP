package dev.mammet.weatherkmp.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun <T> LineGraph(
    modifier: Modifier = Modifier,
    dataPoints: List<T>,
    xValueMapper: (T) -> String,
    yValueMapper: (T) -> Float,
    graphTitle: String = "",
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    labelFontSize: TextUnit = 12.sp,
    gradientColors: List<Color> = listOf(
        MaterialTheme.colorScheme.surfaceContainerHighest,
        Color.Transparent
    )
){
    val padding = 16.dp
    val textMeasurer = rememberTextMeasurer()
    Canvas(modifier = modifier.padding(padding)){
        val maxWidth = size.width
        val maxHeight = size.height

        val xAxisPadding = 60f
        val yAxizPadding = 80f
        val graphPadding = 50f
        val graphWidth = maxWidth - yAxizPadding - graphPadding
        val graphHeight = maxHeight - xAxisPadding - graphPadding

        val yValues = dataPoints.map(yValueMapper)
        val maxYValue = yValues.maxOrNull() ?: 0f
        val minYValue = yValues.minOrNull() ?: 0f
        val yRange = maxYValue - minYValue

        val xInterval = graphWidth / (dataPoints.size -1).coerceAtLeast(1)
        val yMinPadding = minYValue - (yRange * 0.1f) // небольшой отступ снизу
        val yMaxPadding = maxYValue + (yRange * 0.1f) // небольшой отступ сверху
        val adjustedYRange = yMaxPadding - yMinPadding
        val yUnit = if (adjustedYRange > 0) graphHeight / adjustedYRange else 0f

        val points = dataPoints.mapIndexed { index, data ->
            Offset(
                x = yAxizPadding + index * xInterval,
                y = maxHeight - graphPadding - (yValueMapper(data) - yMinPadding) * yUnit
            )
        }
        val path = Path().apply {
            points.forEachIndexed { index, point ->
                if (index == 0) { moveTo(point.x, maxHeight - xAxisPadding) }
                lineTo(point.x, point.y)
            }
            lineTo(points.last().x, maxHeight - xAxisPadding)
            close()
        }
        drawPath(
            path = path,
            brush = Brush.verticalGradient(
                colors = gradientColors,
                startY = maxHeight - xAxisPadding,
                endY = graphPadding
            )
        )
        points.zipWithNext{ start, end ->
            drawLine(
                color = color,
                start = start,
                end = end,
                strokeWidth = 3f
            )
        }

        val labelStep = dataPoints.size / 6
        dataPoints.forEachIndexed { index, data ->
            val xPosition = yAxizPadding + index * xInterval
            val labelOffset =
                Offset(xPosition - labelFontSize.value / 2, maxHeight - xAxisPadding + 10f)
            if (index % labelStep == 0){
                drawText(
                    textMeasurer = textMeasurer,
                    text = xValueMapper(data),
                    topLeft = labelOffset,
                    style = TextStyle(color = color)
                )
            }
        }

        val yLabelStep = yRange / 5
        for (i in 0..5){
            val yValue = minYValue + i * yLabelStep
            val yPosition = maxHeight - xAxisPadding - i * (graphHeight / 5)
            drawText(
                textMeasurer = textMeasurer,
                text = yValue.roundToInt().toString(),
                topLeft = Offset(
                    yAxizPadding / 2 - labelFontSize.value,
                    yPosition - labelFontSize.value / 2
                ),
                style = TextStyle(color = color)
            )
        }

        if (graphTitle.isNotEmpty()){
            drawText(
                textMeasurer = textMeasurer,
                text = graphTitle,
                topLeft = Offset(
                    maxWidth / 2 - textMeasurer.measure(graphTitle).size.width / 2,
                    graphPadding / 2
                ),
                style = TextStyle(color = color,fontWeight = FontWeight.Bold, fontSize = (labelFontSize * 1.2))
            )
        }


    }

}