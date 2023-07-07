package com.demacia.test.ui.chart

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.demacia.test.ui.chart.linechart.data.ChartData
import com.demacia.test.ui.chart.linechart.data.chartPreviewData
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

            lineChart.description.isEnabled = false
            lineChart.legend.isEnabled = false
            lineChart.data = lineData
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
