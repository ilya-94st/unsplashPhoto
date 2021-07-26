package com.example.staselovich_p3_l1.ui.favorites_fragment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.staselovich_p3_l1.dataBase.EntyImage
import com.example.staselovich_p3_l1.model.UnsplashPhoto
import com.example.staselovich_p3_l1.model.UnsplashRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoritesImageViewModel @ViewModelInject constructor(
    val repository: UnsplashRepository
) : ViewModel() {
    lateinit var photo: List<UnsplashPhoto>
    val allFavoritePhotos: LiveData<List<EntyImage>> = repository.readAllImage
    suspend fun getPhotoById(id: String) {
        photo = listOf(repository.getPhotoById(id))
    }
    @DelicateCoroutinesApi
    fun delteImages(image: EntyImage) {
        GlobalScope.launch {
            repository.deleteImage(image)
        }
    }
}