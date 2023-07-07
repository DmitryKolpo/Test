package com.demacia.test.ui.screens.chart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.demacia.test.ui.screens.chart.state.UiState
import com.demacia.test.ui.components.linechart.LineChart
import com.demacia.test.ui.components.Table
import com.demacia.test.ui.components.linechart.data.chartPreviewData
import com.demacia.test.ui.theme.TestTheme

@Composable
fun ChartScreen(
    viewModel: ChartViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.collectAsState(initial = null).value
        ?: return Box(modifier = Modifier.fillMaxSize())

    Content(
        uiState = uiState,
    )
}

@Composable
private fun Content(
    uiState: UiState,
) {
    Column {
        Table(
            points = uiState.points,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .weight(1f)
        )
        LineChart(
            chartData = uiState.chartData,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .weight(1f)
                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun Preview() {
    TestTheme {
        Content(
            uiState = UiState(
                points = emptyList(),
                chartData = chartPreviewData,
            )
        )
    }
}