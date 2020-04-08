package com.foreknowledge.navermaptest.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.foreknowledge.navermaptest.model.data.MarkerPos

/**
 * Create by Yeji on 08,April,2020.
 */
@Entity
data class MarkerEntity (
    val lat: Double,
    val lng: Double,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L
) {
    companion object {
        fun fromMarkerPos(markerPos: MarkerPos) =
            MarkerEntity(markerPos.lat, markerPos.lng, markerPos.id)
    }

    fun toMarker(): MarkerPos = MarkerPos(lat, lng, id)
}