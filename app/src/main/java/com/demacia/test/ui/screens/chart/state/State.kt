package com.demacia.test.ui.screens.chart.state

import com.demacia.test.domain.model.Point

data class State(
    val points: List<Point> = emptyList(),
)