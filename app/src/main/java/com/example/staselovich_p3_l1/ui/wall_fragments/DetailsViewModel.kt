package com.example.staselovich_p3_l1.ui.wall_fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.staselovich_p3_l1.base.BaseVM
import com.example.staselovich_p3_l1.dataBase.EntyImage
import com.example.staselovich_p3_l1.model.UnsplashPhoto
import com.example.staselovich_p3_l1.model.UnsplashRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DetailsViewModel @ViewModelInject constructor(
    val repository: UnsplashRepository
) : BaseVM() {

    val loadingEnded = MutableLiveData<Boolean>()
    @Bindable
    var saveArgs: UnsplashPhoto? = null
    set(value) {
        field = value
        notifyPropertyChanged(BR.saveArgs)
    }

    @get:Bindable
    val formattedDate: String
        @RequiresApi(Build.VERSION_CODES.O)
        get() {
            val photoDate = LocalDate.parse(saveArgs?.createdAt?.substring(0,10), DateTimeFormatter.ISO_DATE)
            val formattedDate = photoDate.format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy")).toString().replace("-", " ")
            return formattedDate
        }

    fun addImage(image: EntyImage): Boolean {
        var isAdded = false
        viewModelScope.launch(Dispatchers.Default) {
            if (!isPhotoExists(image.imgId)) {
                repository.addImages(image)
                isAdded = true
            } else isAdded = false
            loadingEnded.postValue(true)
        }
        return isAdded
    }

    fun returnText() {
        saveArgs?.description
    }

    var isExpanded = false

    fun toggleDescription() {
        isExpanded = !isExpanded
        notifyPropertyChanged(BR.description)
    }

    @get:Bindable
    val description: String?
        get() = if(isExpanded) saveArgs?.description else getSafeSubstring(30)

    fun getSafeSubstring(maxLength: Int): String {
        if (!saveArgs?.description.isNullOrEmpty()) {
            if (saveArgs?.description!!.length >= maxLength) {
                return saveArgs!!.description!!.substring(0, maxLength)
            }
        } else {
            saveArgs?.description = "No Description"
        }
        return saveArgs!!.description.toString()
    }
    private fun isPhotoExists(id: String): Boolean {
        return repository.isImagesExists(id)
    }
    @SuppressLint("StringFormatInvalid")
    fun share(): Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT,  saveArgs?.urls?.regular)
        return shareIntent
    }
}