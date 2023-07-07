package com.demacia.test.ui.chart.state

import com.demacia.test.domain.model.Point
import com.demacia.test.ui.chart.linechart.data.ChartData

data class UiState(
    val count: String,
    val points: List<Point>,
    val chartData: ChartData,
)