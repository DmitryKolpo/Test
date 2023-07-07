package com.demacia.test.ui.screens.main

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.demacia.test.R
import com.demacia.test.navigation.NavScreens
import com.demacia.test.ui.screens.main.state.Effect
import com.demacia.test.ui.screens.main.state.UiState
import com.demacia.test.ui.theme.TestTheme
import com.demacia.test.ui.uiutils.Spacer
import com.demacia.test.ui.uiutils.resource
import com.demacia.test.ui.uiutils.showToast

@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val oopsMsg = R.string.oops.resource()

    LaunchedEffect(viewModel) {
        viewModel.container.sideEffectFlow.collect {
            when (it) {
                is Effect.ShowError -> context.showToast(oopsMsg)
                is Effect.OpenChartScreen -> navController.navigate(NavScreens.chartNavScreen)
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
    handleIntent: (intent: MainViewModel.Intent) -> Unit,
) {
    val countText = remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(
            value = countText.value,
            onValueChange = { countText.value = it },
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
                            onClick = { countText.value = "" },
                        )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        )
        Spacer(1f)
        if (uiState.loading) CircularProgressIndicator()
        Spacer(1f)
        Button(
            onClick = { handleIntent(MainViewModel.Intent.OnGoClick(countText.value)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(
                text = R.string.go.resource(),
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    val uiState = UiState(
        loading = false,
    )

    TestTheme {
        Content(uiState) {}
    }
}