package com.example.staselovich_p3_l1.api

import com.example.staselovich_p3_l1.model.UnsplashPhoto

data class UnsplashResponse( val total: Int,
    val results: List<UnsplashPhoto>

) {
}