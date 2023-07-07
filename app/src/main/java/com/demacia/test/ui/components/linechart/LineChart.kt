package com.demacia.test.ui.components.linechart

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.demacia.test.ui.components.linechart.data.ChartData
import com.demacia.test.ui.components.linechart.data.chartPreviewData
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

@Composable
fun LineChart(
    chartData: ChartData,
    modifier: Modifier = Modifier,
) {
    AndroidView(
        modifier = modifier,
        factory = { context -> LineChart(context) },
        update = { lineChart ->
            val entries = chartData.entries.map { entry ->
                Entry(entry.x.toFloat(), entry.y.toFloat())
            }
            val dataSet = LineDataSet(entries, "").apply {
                lineWidth = 1.5f

                setDrawValues(false)
                setDrawCircles(false)

                mode = LineDataSet.Mode.HORIZONTAL_BEZIER
                cubicIntensity = 0.5f
            }
            val lineData = LineData(dataSet)

            lineChart.setScaleEnabled(true)
            lineChart.description.isEnabled = false
            lineChart.legend.isEnabled = false
            lineChart.data = lineData

            //TODO: to save chart as file use this
            // Don't forget to set callbacks, handle errors, add storage permission etc
//            lineChart.saveToGallery("chart")

            lineChart.invalidate()
        }
    )
}

@Preview
@Composable
private fun Preview() {
    LineChart(
        chartData = chartPreviewData,
    )
}
