package com.foreknowledge.navermaptest.util

import com.foreknowledge.navermaptest.model.data.UserMarker
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.overlay.Marker

/**
 * Create by Yeji on 08,April,2020.
 */
object MarkerUtil {
    fun createUserMarker(
        lat: Double,
        lng: Double,
        id: Long = 0L,
        onClick:(userMarker: UserMarker) -> Boolean
    ) = UserMarker(Marker(), id).apply {
        marker.position = LatLng(lat, lng)
        marker.setOnClickListener {
            onClick(this)
        }
    }

    fun detachUnsavedMarker(userMarker: UserMarker?) {
        if (userMarker != null && userMarker.id == 0L)
            userMarker.marker.map = null
    }
}