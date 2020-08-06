package com.foreknowledge.navermaptest.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by Yeji on 08,April,2020.
 */
@Database(entities = [PlaceEntity::class], version = 1, exportSchema = false)
abstract class DatabaseService: RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "place.db"

        private var instance: DatabaseService? = null

        private fun create(context: Context): DatabaseService =
            Room.databaseBuilder(context, DatabaseService::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()


        fun getInstance(context: Context): DatabaseService =
            (instance ?: create(context)).also { instance = it }
    }

    abstract fun placeDao(): PlaceDao
}