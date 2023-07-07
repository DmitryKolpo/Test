package com.demacia.test.ui.screens.main.state

import com.demacia.test.domain.model.Point

data class State(
    val pointsLoading: Boolean = false,
    val points: List<Point> = emptyList(),
    val pointsError: Throwable? = null,
)
