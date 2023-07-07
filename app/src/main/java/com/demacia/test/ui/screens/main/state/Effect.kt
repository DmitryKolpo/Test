package com.demacia.test.ui.screens.main.state

sealed interface Effect {
    object ShowError : Effect
    object OpenChartScreen : Effect
}