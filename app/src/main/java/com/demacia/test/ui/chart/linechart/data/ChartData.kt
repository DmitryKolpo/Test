package com.demacia.test.ui.chart.linechart.data

import java.math.BigDecimal

data class ChartData(
    val entries: List<Entry> = emptyList(),
) {
    data class Entry(
        val x: BigDecimal,
        val y: BigDecimal,
    )
}

