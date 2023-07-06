package com.demacia.test.ui.chart.state

import com.demacia.test.domain.model.Point

data class State(
    val pointsLoading: Boolean = false,
    val points: List<Point> = emptyList(),
    val pointsError: Throwable? = null,
)
