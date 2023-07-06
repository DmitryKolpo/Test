package com.demacia.test.ui.chart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.demacia.test.ui.chart.state.UiState
import com.demacia.test.ui.theme.TestTheme
import com.demacia.test.ui.uiutils.Spacer

@Composable
fun ChartScreen(
    viewModel: ChartViewModel = viewModel()
) {
    val uiState = viewModel.uiState.collectAsState(initial = null).value
        ?: return Box(modifier = Modifier.fillMaxSize())

    Content(
        uiState = uiState,
        handleIntent = viewModel::handleIntent,
    )
}

@Composable
private fun Content(
    uiState: UiState,
    handleIntent: (intent: ChartViewModel.Intent) -> Unit,
) {
    Column() {
        Spacer(1f)
        Button(
            onClick = { handleIntent(ChartViewModel.Intent.OnGoClick) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(
                text = "Go",
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    val uiState = UiState(emptyList())

    TestTheme {
        Content(uiState) {}
    }
}