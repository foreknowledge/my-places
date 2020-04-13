package com.foreknowledge.navermaptest.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.foreknowledge.navermaptest.model.data.UserMarker

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
        fun fromUserMarker(userMarker: UserMarker) =
            MarkerEntity(userMarker.lat, userMarker.lng, userMarker.id)
    }

    fun toUserMarker(): UserMarker = UserMarker(lat, lng, id)
}