package com.demacia.test.ui.chart

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.demacia.test.R
import com.demacia.test.ui.chart.linechart.data.chartPreviewData
import com.demacia.test.ui.chart.state.Effect
import com.demacia.test.ui.chart.state.UiState
import com.demacia.test.ui.theme.TestTheme
import com.demacia.test.ui.uiutils.resource
import com.demacia.test.ui.uiutils.showToast

@Composable
fun ChartScreen(
    viewModel: ChartViewModel = viewModel()
) {
    val context = LocalContext.current
    val oopsMsg = R.string.oops.resource()

    LaunchedEffect(viewModel) {
        viewModel.container.sideEffectFlow.collect {
            when (it) {
                is Effect.ShowError -> context.showToast(oopsMsg)
            }
        }
    }

    val uiState = viewModel.uiState.collectAsState(initial = null).value
        ?: return Box(modifier = Modifier.fillMaxSize())

    Content(
        uiState = uiState,
        handleIntent = viewModel::handleIntent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    uiState: UiState,
    handleIntent: (intent: ChartViewModel.Intent) -> Unit,
) {
    Column() {
        TextField(
            value = uiState.count,
            onValueChange = { handleIntent(ChartViewModel.Intent.OnInputChange(it)) },
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Number,
            ),
            supportingText = {
                Text(
                    text = R.string.input_supporting_text.resource(),
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(false, 24.dp),
                            onClick = { handleIntent(ChartViewModel.Intent.OnInputClearClick) },
                        )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        )
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
    val uiState = UiState(
        count = "1",
        points = emptyList(),
        chartData = chartPreviewData,
    )

    TestTheme {
        Content(uiState) {}
    }
}