package com.example.staselovich_p3_l1.api

import com.example.staselovich_p3_l1.model.UnsplashPhoto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query



interface UnsplashApi {
    companion object{
        const val BASE_URL = "https://api.unsplash.com/"
        const val CLIENT_ID = "ehAi_RYbgeyGaHBHI-Sn2DkP1ftNdso04sY00I1dXAc"
    }

    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UnsplashResponse

    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET("photos/{id}")
    suspend fun searchPhotoById(
        @Path("id") id: String,
    ): UnsplashPhoto

}