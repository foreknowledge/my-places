package com.foreknowledge.navermaptest.model.repository

import android.content.Context
import com.foreknowledge.navermaptest.model.data.Place
import com.foreknowledge.navermaptest.model.room.DatabaseService
import com.foreknowledge.navermaptest.model.room.PlaceEntity

/**
 * Created by Yeji on 08,April,2020.
 */
class PlaceDataSource(context: Context) {
    private val placeDao = DatabaseService.getInstance(context).placeDao()

    suspend fun getAll(): List<Place> = placeDao.getAllPlaceEntities().map { it.toPlace() }

    suspend fun add(placeEntity: PlaceEntity) = placeDao.addPlaceEntity(placeEntity)

    suspend fun delete(placeEntity: PlaceEntity) = placeDao.deletePlaceEntity(placeEntity)
}