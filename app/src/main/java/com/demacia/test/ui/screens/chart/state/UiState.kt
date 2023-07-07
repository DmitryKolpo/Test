package com.demacia.test.ui.screens.chart.state

import com.demacia.test.domain.model.Point
import com.demacia.test.ui.components.linechart.data.ChartData

data class UiState(
    val points: List<Point>,
    val chartData: ChartData,
)
