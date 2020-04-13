package com.foreknowledge.navermaptest.model.repository

import android.content.Context
import android.util.Log
import com.foreknowledge.navermaptest.model.data.UserMarker

/**
 * Create by Yeji on 08,April,2020.
 */
class NaverRepository(context: Context) {
    private val markerDataSource = MarkerDataSource(context)

    suspend fun addMarker(userMarker: UserMarker) {
        markerDataSource.add(userMarker)
        Log.d("NaverMapTest", "marker added. id = ${userMarker.id}")
    }
    suspend fun getAllMarkers() =
        markerDataSource.getAll().apply {
            forEach { Log.d("NaverMapTest", "marker fetched. id = ${it.id}") }
        }

    suspend fun deleteMarker(userMarker: UserMarker) {
        markerDataSource.delete(userMarker)
        Log.d("NaverMapTest", "marker deleted. id = ${userMarker.id}")
    }
}