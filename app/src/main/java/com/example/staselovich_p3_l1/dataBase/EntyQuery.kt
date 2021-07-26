package com.example.staselovich_p3_l1.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "query_table")
data class EntyQuery(
    @PrimaryKey
    val queryText: String,
    val liked: Boolean,
    val total: Int,
    val date: String

)
