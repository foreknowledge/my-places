package com.foreknowledge.navermaptest.model.room

import androidx.room.*

/**
 * Created by Yeji on 08,April,2020.
 */
@Dao
interface PlaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlaceEntity(placeEntity: PlaceEntity): Long

    @Query("SELECT * FROM PlaceEntity")
    suspend fun getAllPlaceEntities(): List<PlaceEntity>

    @Delete
    suspend fun deletePlaceEntity(placeEntity: PlaceEntity)
}