package com.demacia.test.data.api

import com.demacia.test.data.api.models.PointsResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetApi {

    private val api: Api by lazy { initApi() }
    private lateinit var retrofit: Retrofit

    suspend fun getPoints(count: Int): PointsResponse {
        return api.getPoints(count)
    }

    private fun initApi(): Api {
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(Api::class.java)
    }

    private companion object {
        const val baseUrl = "https://hr-challenge.interactivestandard.com/"
    }
}