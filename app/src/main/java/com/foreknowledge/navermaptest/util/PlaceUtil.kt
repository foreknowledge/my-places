package com.foreknowledge.navermaptest.util

import androidx.core.content.ContextCompat
import com.foreknowledge.navermaptest.GlobalApplication
import com.foreknowledge.navermaptest.R
import com.foreknowledge.navermaptest.model.data.Place
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.MarkerIcons
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Yeji on 08,April,2020.
 */
object PlaceUtil {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val colorGreen = ContextCompat.getColor(GlobalApplication.getContext(), R.color.colorMarkerGreen)
    private val colorRed = ContextCompat.getColor(GlobalApplication.getContext(), R.color.colorMarkerRed)

    fun createPlace(
        lat: Double,
        lng: Double,
        id: Long = 0L,
        onClick:(place: Place) -> Boolean
    ) = Place(id, Marker()).apply {
        marker.position = LatLng(lat, lng)
        marker.icon = MarkerIcons.BLACK
        marker.iconTintColor = colorGreen
        marker.setOnClickListener {
            onClick(this)
        }
    }

    fun detachUnsavedPlace(place: Place?) = coroutineScope.launch {
        if (place != null && place.id == 0L)
            place.marker.map = null
    }

    fun getFocus(marker: Marker?) = coroutineScope.launch {
        marker?.iconTintColor = colorRed
    }

    fun loseFocus(marker: Marker?) = coroutineScope.launch {
        marker?.iconTintColor = colorGreen
    }

}