package com.example.staselovich_p3_l1.model

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.staselovich_p3_l1.api.UnsplashApi
import com.example.staselovich_p3_l1.dataBase.DaoImage
import com.example.staselovich_p3_l1.dataBase.EntyImage
import com.example.staselovich_p3_l1.dataBase.EntyQuery
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepository @Inject constructor(private val unsplashApi:UnsplashApi, private val images: DaoImage
) {
    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {UnsplashPagingsourse(unsplashApi,query)}
        ).liveData
    suspend fun getTotalSearchResults(query: String) = unsplashApi.searchPhotos(query, 1,1).total
    val readAllImage: LiveData<List<EntyImage>> = images.readAll()
    val readAllQury: LiveData<List<EntyQuery>> = images.readAllQuery()
    val readFavoriteQueris: LiveData<List<EntyQuery>> = images.getLikedQueries()
    suspend fun getPhotoById(id: String) =
        unsplashApi.searchPhotoById(id)

      fun addImages(image: EntyImage){
        images.addImage(image)
    }
     fun deleteImage(image: EntyImage){
        images.deleteImage(image)
    }

    fun isImagesExists(id: String): Boolean {
        return images.isPhotoExists(id)
    }

    fun addQueris(query: EntyQuery){
        images.addQuery(query)
    }
    fun changeQueryLikeState(query: EntyQuery) {
        images.changeQueryLikeState(query)
    }

}