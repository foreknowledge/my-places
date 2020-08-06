package com.foreknowledge.navermaptest.model.repository

import android.content.Context
import android.util.Log
import com.foreknowledge.navermaptest.network.GeoResponse
import com.foreknowledge.navermaptest.model.room.PlaceEntity
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.Utmk

/**
 * Created by Yeji on 08,April,2020.
 */
class NaverRepository(context: Context) {
    private val TAG = NaverRepository::class.java.simpleName
    private val placeDataSource = PlaceDataSource(context)
    private val geoDataSource = GeoDataSource

    suspend fun getAllPlaces() =
        placeDataSource.getAll().apply {
            forEach { Log.d(TAG, "place fetched. marker id = ${it.id}") }
        }

    suspend fun addPlace(lat: Double, lng: Double): Long {
        val id = placeDataSource.add(PlaceEntity(lat, lng))
        Log.d(TAG, "place added. marker id = $id")
        return id
    }

    suspend fun deletePlace(lat: Double, lng: Double, id: Long) {
        placeDataSource.delete(PlaceEntity(lat, lng, id))
        Log.d(TAG, "place deleted. marker id = $id")
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