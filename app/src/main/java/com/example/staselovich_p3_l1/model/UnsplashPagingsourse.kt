package com.example.staselovich_p3_l1.model

import android.util.Log
import androidx.paging.PagingSource
import com.example.staselovich_p3_l1.api.UnsplashApi
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

private const val UNSPLASH_STARTING_PAGE_INDEX = 1
class UnsplashPagingsourse(
    private val unsplashApi: UnsplashApi,
    private val query : String


): PagingSource<Int, UnsplashPhoto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
     val position = params.key?: UNSPLASH_STARTING_PAGE_INDEX
return try {
    val response = unsplashApi.searchPhotos(query,position, params.loadSize)
    val photos = response.results
    Log.e("asd", photos.size.toString())
    LoadResult.Page(
        data = photos,
        prevKey = if(position== UNSPLASH_STARTING_PAGE_INDEX) null else position -1,
        nextKey = if(photos.isEmpty()) null else position +1
    )
} catch (exption: IOException){
LoadResult.Error(exption)
} catch (exeption: HttpException){
    LoadResult.Error(exeption)
}
    }
}