package com.demacia.test.api

import com.demacia.test.api.models.PointsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface Api {

    @GET("api/test/points")
    fun getPoints(): Single<PointsResponse>
}