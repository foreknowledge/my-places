package com.foreknowledge.navermaptest.util

import androidx.lifecycle.MutableLiveData
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker

/**
 * Create by Yeji on 08,April,2020.
 */
object MarkerUtil {
    private val markers = MutableLiveData<MutableList<Marker>>()

    init {
        markers.value = mutableListOf()
    }

    fun createAndAttachMarker(
        lat: Double,
        lng: Double,
        naverMap: NaverMap,
        onClick:(marker: Marker) -> Boolean
    ) = Marker().apply {
        position = LatLng(lat, lng)
        map = naverMap
        setOnClickListener {
            onClick(this)
        }
    }

    fun insertToList(marker: Marker) {
        markers.value?.let {
            it.add(marker)
            ToastUtil.showToast("marker saved.")
        }
    }

    fun removeFromList(marker: Marker): Boolean {
        markers.value?.let { markers ->
            markers.remove(marker)
            marker.map = null
            ToastUtil.showToast("marker deleted.")
            return true
        }
        return false
    }

    fun detachUnsavedMarker(marker: Marker?) {
        markers.value?.let { markers ->
            if (marker != null && !markers.contains(marker))
                marker.map = null
        }
    }
}