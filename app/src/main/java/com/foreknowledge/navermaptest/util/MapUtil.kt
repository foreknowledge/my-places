package com.foreknowledge.navermaptest.util

import com.naver.maps.geometry.LatLng
import com.naver.maps.map.overlay.Marker

/**
 * Create by Yeji on 08,April,2020.
 */
object MapUtil {
    fun createMarker(lat: Double, lng: Double) =
        Marker().apply {
            position = LatLng(lat, lng)
            setOnClickListener {
                map = null
                true
            }
        }
}