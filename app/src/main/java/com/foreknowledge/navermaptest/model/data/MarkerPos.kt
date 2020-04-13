package com.foreknowledge.navermaptest.model.data

import com.naver.maps.map.overlay.Marker

/**
 * Create by Yeji on 08,April,2020.
 */
data class MarkerPos (
    val lat: Double,
    val lng: Double,
    val id: Long = 0L
) {
    companion object {
        fun fromMarker(marker: Marker) =
            MarkerPos(marker.position.latitude, marker.position.longitude)
    }
}