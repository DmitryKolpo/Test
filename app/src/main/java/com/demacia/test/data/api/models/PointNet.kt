package com.demacia.test.data.api.models

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class PointsResponse(
    @SerializedName("points")
    val points: List<PointNet>,
)

data class PointNet(
    @SerializedName("x")
    val x: BigDecimal,
    @SerializedName("y")
    val y: BigDecimal,
)
