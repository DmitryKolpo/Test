package com.demacia.test.ui.chart.state

import com.demacia.test.domain.model.Point

data class UiState(
    val count: String,
    val points: List<Point>,
)