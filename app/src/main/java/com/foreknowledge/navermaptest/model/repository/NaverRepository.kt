package com.foreknowledge.navermaptest.model.repository

import android.content.Context
import android.util.Log
import com.foreknowledge.navermaptest.network.GeoResponse
import com.foreknowledge.navermaptest.model.room.MarkerEntity
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.Utmk

/**
 * Create by Yeji on 08,April,2020.
 */
class NaverRepository(context: Context) {
    private val TAG = NaverRepository::class.java.simpleName
    private val markerDataSource = MarkerDataSource(context)
    private val geoDataSource = GeoDataSource

    suspend fun getAllMarkers() =
        markerDataSource.getAll().apply {
            forEach { Log.d(TAG, "marker fetched. marker id = ${it.id}") }
        }

    suspend fun addMarker(lat: Double, lng: Double): Long {
        val id = markerDataSource.add(MarkerEntity(lat, lng))
        Log.d(TAG, "marker added. marker id = $id")
        return id
    }

    suspend fun deleteMarker(lat: Double, lng: Double, id: Long) {
        markerDataSource.delete(MarkerEntity(lat, lng, id))
        Log.d(TAG, "marker deleted. marker id = $id")
    }

    fun getAddressInfo(
        lat: Double, lng: Double,
        success: (response: GeoResponse?) -> Unit,
        failure: (String, String) -> Unit
    ) {
        val utmk = Utmk.valueOf(LatLng(lat, lng))
        geoDataSource.requestAddr(utmk.x, utmk.y, success, failure)
    }
}