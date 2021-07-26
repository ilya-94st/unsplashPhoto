package com.example.staselovich_p3_l1.ui.history_fragment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.staselovich_p3_l1.dataBase.EntyQuery
import com.example.staselovich_p3_l1.model.UnsplashRepository


class ViewModelHistory @ViewModelInject constructor(
    val repository: UnsplashRepository
) : ViewModel() {
    val allEntyQuery: LiveData<List<EntyQuery>> = repository.readAllQury
    fun changeQueryLikeState(query: EntyQuery) {
        repository.changeQueryLikeState(query)
    }
}