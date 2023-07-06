package com.demacia.test.api.models

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class PointsResponse(
    @SerializedName("points")
    val points: List<Point>,
)

data class Point(
    @SerializedName("x")
    val x: BigDecimal,
    @SerializedName("y")
    val y: BigDecimal,
)
