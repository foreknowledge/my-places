package com.foreknowledge.navermaptest.model.repository

import android.content.Context
import com.foreknowledge.navermaptest.model.data.MarkerPos
import com.foreknowledge.navermaptest.model.room.DatabaseService
import com.foreknowledge.navermaptest.model.room.MarkerEntity

/**
 * Create by Yeji on 08,April,2020.
 */
class MarkerDataSource(context: Context) {
    private val markerDao = DatabaseService.getInstance(context).markerDao()

    suspend fun getAll(): List<MarkerPos> = markerDao.getAllNoteEntities().map { it.toMarker() }

    suspend fun add(markerPos: MarkerPos) = markerDao.addNoteEntity(MarkerEntity.fromMarkerPos(markerPos))

    suspend fun delete(markerPos: MarkerPos) = markerDao.deleteNoteEntity(MarkerEntity.fromMarkerPos(markerPos))
}