package com.demacia.test.ui.chart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
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
            //TODO: add clear button
            supportingText = {
                Text(
                    text = "How many points do you want to display?",
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
    )

    TestTheme {
        Content(uiState) {}
    }
}