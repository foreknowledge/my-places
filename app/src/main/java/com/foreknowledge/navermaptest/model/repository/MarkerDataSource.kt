package com.foreknowledge.navermaptest.model.repository

import android.content.Context
import com.foreknowledge.navermaptest.model.data.UserMarker
import com.foreknowledge.navermaptest.model.room.DatabaseService
import com.foreknowledge.navermaptest.model.room.MarkerEntity

/**
 * Create by Yeji on 08,April,2020.
 */
class MarkerDataSource(context: Context) {
    private val markerDao = DatabaseService.getInstance(context).markerDao()

    suspend fun getAll(): List<UserMarker> = markerDao.getAllMarkerEntities().map { it.toUserMarker() }

    suspend fun add(markerEntity: MarkerEntity) = markerDao.addMarkerEntity(markerEntity)

    suspend fun delete(markerEntity: MarkerEntity) = markerDao.deleteMarkerEntity(markerEntity)
}