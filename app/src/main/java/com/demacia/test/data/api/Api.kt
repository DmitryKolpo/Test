package com.demacia.test.data.api

import com.demacia.test.data.api.models.PointsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("api/test/points")
    suspend fun getPoints(
        @Query("count") count: Int,
    ): PointsResponse

    @GET("api/test/text/android")
    suspend fun getText(): String
}