package com.example.staselovich_p3_l1.dataBase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DaoImage {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun addImage(image: EntyImage)

    @Query("SELECT * FROM image_table ORDER BY id ASC")
    fun readAll(): LiveData<List<EntyImage>>

    @Delete
    fun deleteImage(image: EntyImage)

    @Query("DELETE FROM image_table")
    suspend fun deleteAllImages()

    @Query("SELECT EXISTS (SELECT 1 FROM image_table WHERE imgId = :id)")
    fun isPhotoExists(id: String): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addQuery(query: EntyQuery)

    @Query("Select * FROM query_table ORDER BY date ASC")
    fun readAllQuery():LiveData<List<EntyQuery>>

    @Query("Select * FROM query_table WHERE liked = 1")
    fun getLikedQueries():LiveData<List<EntyQuery>>

    @Update
    fun changeQueryLikeState(query: EntyQuery)

}