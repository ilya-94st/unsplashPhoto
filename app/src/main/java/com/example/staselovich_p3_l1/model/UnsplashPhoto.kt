package com.example.staselovich_p3_l1.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class UnsplashPhoto(
    val id: String,
    var description: String?,
    val width: Int,
    val height: Int,
    @SerializedName("created_at")
    val createdAt: String,
    val color: String,
    val urls: UnsplashPhotoUrls,
    val user: UnsplashUser

) : Parcelable {
    @Parcelize
    data class UnsplashPhotoUrls(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String
    ) : Parcelable {}

    @Parcelize
    data class UnsplashUser(
        val name: String,
        val username: String,
        val instagram_username: String?,
        val twitter_name: String?,
        val portfolio_url: String?,
        val profile_image: UnsplashUserPhotoUrl
    ) : Parcelable {
        @Parcelize
        data class UnsplashUserPhotoUrl(
            val small: String?,
            val medium: String?,
            val large: String?
        ) : Parcelable

        val attributionUrl get() = "https://unsplash.com/$username?utm_source=ImageSearchApp&utm_medium=referral"
    }
}

