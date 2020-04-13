package com.foreknowledge.navermaptest.model.repository

import android.content.Context
import android.util.Log
import com.foreknowledge.navermaptest.model.data.MarkerPos

/**
 * Create by Yeji on 08,April,2020.
 */
class NaverRepository(context: Context) {
    private val markerDataSource = MarkerDataSource(context)

    suspend fun addMarker(markerPos: MarkerPos) {
        markerDataSource.add(markerPos)
        Log.d("NaverMapTest", "marker added. id = ${markerPos.id}")
    }
    suspend fun getAllMarkers() =
        markerDataSource.getAll().apply {
            forEach { Log.d("NaverMapTest", "marker fetched. id = ${it.id}") }
        }

    suspend fun deleteMarker(markerPos: MarkerPos) {
        markerDataSource.delete(markerPos)
        Log.d("NaverMapTest", "marker deleted. id = ${markerPos.id}")
    }
}