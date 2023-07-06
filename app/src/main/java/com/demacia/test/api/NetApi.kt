package com.demacia.test.api

import com.demacia.test.api.models.PointsResponse
import com.demacia.test.utils.subscribeOnIo
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class NetApi {

    private val api: Api by lazy { initApi() }
    private lateinit var httpClient: OkHttpClient
    private lateinit var retrofit: Retrofit

    fun getPoints(): Single<PointsResponse> {
        return api.getPoints()
            //TODO: handle error
            .subscribeOnIo()
    }

    private fun initApi(): Api {
        httpClient = OkHttpClient.Builder().config().build()
        retrofit = Retrofit.Builder().config().build()

        return retrofit.create(Api::class.java)
    }

    private fun OkHttpClient.Builder.config(): OkHttpClient.Builder {
        //Add necessary interceptors
        return this
    }

    private fun Retrofit.Builder.config(): Retrofit.Builder {
        return addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//            .addConverterFactory(converterFactory)
            .baseUrl(baseUrl)
            .client(httpClient)
    }

    companion object {
        const val baseUrl = "https://hr-challenge.interactivestandard.com/"
    }
}