package com.demacia.test.ui.components.linechart.data

import java.math.BigDecimal

val chartPreviewData: ChartData
    get() = ChartData(
        listOf(
            ChartData.Entry(BigDecimal(12.04), BigDecimal(22.04)),
            ChartData.Entry(BigDecimal(-22.01), BigDecimal(-22.04)),
            ChartData.Entry(BigDecimal(35.74), BigDecimal(22.04)),
            ChartData.Entry(BigDecimal(12.04), BigDecimal(22.04)),
            ChartData.Entry(BigDecimal(12.04), BigDecimal(-22.04)),
            ChartData.Entry(BigDecimal(-12.04), BigDecimal(22.04)),
        )
    )