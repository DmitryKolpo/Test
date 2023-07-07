package com.demacia.test.ui.chart

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demacia.test.domain.model.Point
import com.demacia.test.ui.theme.TestTheme
import java.math.BigDecimal

@Composable
fun Table(
    points: List<Point>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
    ) {
        // Here is the header
        item {
            Row(Modifier.background(Color.Gray)) {
                TableCell(text = "#", weight = column1Weight)
                TableCell(text = "x", weight = column2Weight)
                TableCell(text = "y", weight = column3Weight)
            }
        }
        items(points.size) { index ->
            val point = points[index]
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = index.toString(), weight = column1Weight)
                TableCell(text = point.x.toString(), weight = column2Weight)
                TableCell(text = point.y.toString(), weight = column3Weight)
            }
        }
    }
}

@Composable
private fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .padding(8.dp)
    )
}

private const val column1Weight = 0.2f
private const val column2Weight = 0.4f
private const val column3Weight = 0.4f

@Preview
@Composable
private fun Preview() {
    TestTheme {
        Table(
            points = listOf(
                Point(BigDecimal.ONE, BigDecimal.ONE),
                Point(BigDecimal.ONE, BigDecimal.ONE),
            )
        )
    }
}