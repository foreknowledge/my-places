package com.foreknowledge.navermaptest.model.repository

import android.content.Context
import android.util.Log
import com.foreknowledge.navermaptest.model.room.MarkerEntity

/**
 * Create by Yeji on 08,April,2020.
 */
class NaverRepository(context: Context) {
    private val markerDataSource = MarkerDataSource(context)

    suspend fun getAllMarkers() =
        markerDataSource.getAll().apply {
            forEach { Log.d("NaverMapTest", "marker fetched. marker id = ${it.id}") }
        }

    suspend fun addMarker(lat: Double, lng: Double): Long {
        val id = markerDataSource.add(MarkerEntity(lat, lng))
        Log.d("NaverMapTest", "marker added. marker id = $id")
        return id
    }

    suspend fun deleteMarker(lat: Double, lng: Double, id: Long) {
        markerDataSource.delete(MarkerEntity(lat, lng, id))
        Log.d("NaverMapTest", "marker deleted. marker id = $id")
    }
}