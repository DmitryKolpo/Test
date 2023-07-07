package com.demacia.test.ui.uiutils

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp

@Composable
fun ColumnScope.Spacer(height: Dp) =
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(height))

@Composable
fun RowScope.Spacer(width: Dp) =
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.width(width))

@Composable
fun RowScope.Spacer(weight: Float) =
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(weight))

@Composable
fun ColumnScope.Spacer(weight: Float) =
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(weight))

@Composable
fun LazyItemScope.Spacer(height: Dp) =
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(height))

@Composable
fun @receiver:StringRes Int.resource(): String =
    resource(
        formatArgs = emptyArray(),
    )

@Composable
fun @receiver:StringRes Int.resource(vararg formatArgs: Any): String =
    stringResource(
        id = this,
        formatArgs = formatArgs,
    )