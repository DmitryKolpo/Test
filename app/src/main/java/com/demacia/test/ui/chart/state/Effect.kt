package com.demacia.test.ui.chart.state

sealed interface Effect {
    object ShowError : Effect
}