package com.example.staselovich_p3_l1.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_table")
data class EntyImage(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var imgId: String,
    var image: String,
)
