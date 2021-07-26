package com.example.staselovich_p3_l1.di

import android.content.Context
import androidx.room.Room
import com.example.staselovich_p3_l1.api.UnsplashApi
import com.example.staselovich_p3_l1.dataBase.DaoImage
import com.example.staselovich_p3_l1.dataBase.DataBaseImage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(UnsplashApi.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build()

    @Provides
    @Singleton
    fun provideUnsplashApi(retrofit: Retrofit): UnsplashApi
    = retrofit.create(UnsplashApi::class.java)

    @Singleton
    @Provides
    fun provideSearchDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            DataBaseImage::class.java,
            "wallpapers_database"
        ).build()

    @Provides
    fun provideSearchDAO(appDatabase: DataBaseImage): DaoImage {
        return appDatabase.imageDao()
    }
}