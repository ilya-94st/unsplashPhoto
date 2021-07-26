package com.example.staselovich_p3_l1.ui.wall_fragments

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.staselovich_p3_l1.base.BaseVM
import com.example.staselovich_p3_l1.dataBase.EntyQuery
import com.example.staselovich_p3_l1.model.UnsplashRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.joda.time.DateTime


class WallViewModel @ViewModelInject constructor(
    private val repositiry: UnsplashRepository): BaseVM() {
        private val currentQuery = MutableLiveData(DEFAULT_QUERY)

       val photos = currentQuery.switchMap { queryString->
           repositiry.getSearchResults(queryString).cachedIn(viewModelScope)
       }

    fun searcPhotos(query: String) {
        currentQuery.value =query
    }


    fun addQuery() {
        viewModelScope.launch(Dispatchers.Default) {
            val total = repositiry.getTotalSearchResults(getCurrentQuery())
            delay(2000)
            repositiry.addQueris(EntyQuery(getCurrentQuery(),false,total,getCurrentQueryDate()))
        }
    }

    fun getCurrentQueryDate(): String {
        return DateTime.now().toString()
    }

    fun getCurrentQuery(): String {
        return currentQuery.value!!
    }

    companion object{
        private const val DEFAULT_QUERY = "cats"
    }
}