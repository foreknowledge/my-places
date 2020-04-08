package com.foreknowledge.navermaptest.model.repository

import android.content.Context
import com.foreknowledge.navermaptest.model.data.MarkerPos

/**
 * Create by Yeji on 08,April,2020.
 */
class NaverRepository(context: Context) {
    private val markerDataSource = MarkerDataSource(context)

    suspend fun addMarker(markerPos: MarkerPos) = markerDataSource.add(markerPos)
    suspend fun getAllMarkers() = markerDataSource.getAll()
    suspend fun deleteMarker(markerPos: MarkerPos) = markerDataSource.delete(markerPos)
}