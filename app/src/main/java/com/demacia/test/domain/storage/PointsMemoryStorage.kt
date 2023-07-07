package com.demacia.test.domain.storage

import com.demacia.test.domain.model.Point
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PointsMemoryStorage @Inject constructor() {

    private var cache: List<Point>? = null

    fun getPoints(): List<Point>? {
        return cache
    }

    fun savePoints(points: List<Point>?) {
        cache = points
    }
}