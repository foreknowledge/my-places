package com.foreknowledge.navermaptest.model.data

import com.naver.maps.map.overlay.Marker

/**
 * Create by Yeji on 08,April,2020.
 */
data class Place (
    var id: Long,
    val marker: Marker,
    var address: String? = null
)