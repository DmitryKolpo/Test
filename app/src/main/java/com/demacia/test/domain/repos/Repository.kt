package com.demacia.test.domain.repos

import com.demacia.test.data.api.NetApi
import com.demacia.test.data.api.models.PointsResponse
import com.demacia.test.domain.model.Point
import com.demacia.test.domain.storage.PointsMemoryStorage
import javax.inject.Inject


class Repository @Inject constructor(
    private val netApi: NetApi,
    private val pointsMemoryStorage: PointsMemoryStorage,
) {
    suspend fun getPoints(count: Int): List<Point> {
        return netApi.getPoints(count)
            .toDomain()
            .also { pointsMemoryStorage.savePoints(it) }
    }

    private fun PointsResponse.toDomain(): List<Point> {
        return points
            .map { Point(x = it.x, y = it.y) }
            .toList()
    }
}