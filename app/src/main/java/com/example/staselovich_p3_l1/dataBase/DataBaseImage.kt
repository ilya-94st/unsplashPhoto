package com.example.staselovich_p3_l1.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(EntyImage::class, EntyQuery::class), version = 1, exportSchema = false)
abstract class DataBaseImage: RoomDatabase() {
    abstract fun imageDao(): DaoImage
}
