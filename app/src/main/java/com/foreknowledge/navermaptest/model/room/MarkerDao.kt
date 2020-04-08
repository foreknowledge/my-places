package com.foreknowledge.navermaptest.model.room

import androidx.room.*

/**
 * Create by Yeji on 08,April,2020.
 */
@Dao
interface MarkerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNoteEntity(markerEntity: MarkerEntity)

    @Query("SELECT * FROM MarkerEntity")
    suspend fun getAllNoteEntities(): List<MarkerEntity>

    @Delete
    suspend fun deleteNoteEntity(markerEntity: MarkerEntity)
}