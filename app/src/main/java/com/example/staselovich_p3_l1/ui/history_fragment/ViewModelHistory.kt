package com.example.staselovich_p3_l1.ui.history_fragment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.staselovich_p3_l1.dataBase.EntyQuery
import com.example.staselovich_p3_l1.model.UnsplashRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.Hours
import org.joda.time.Minutes
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter


class ViewModelHistory @ViewModelInject constructor(
    val repository: UnsplashRepository
) : ViewModel() {
    val allEntyQuery: LiveData<List<EntyQuery>> = repository.readAllQury
    fun changeQueryLikeState(query: EntyQuery) {
        repository.changeQueryLikeState(query)
    }
    fun deleteAllQueris() {
viewModelScope.launch(Dispatchers.IO) {
    repository.deleteAllQueris()
}
    }
    fun compareDates(date: String) : String {
        val currentDate = DateTime.now()
        val queryDate = DateTime.parse(date)

        val daysBetween = Days.daysBetween(queryDate, currentDate)
        val hoursBetween = Hours.hoursBetween(queryDate, currentDate)
        val minutesBetween = Minutes.minutesBetween(queryDate, currentDate)

        val formatter: DateTimeFormatter = DateTimeFormat.forPattern("MMMM")
        val month = formatter.print(queryDate)



        when (daysBetween.days) {
            0 -> when (hoursBetween.hours) {
                0 -> if(minutesBetween.minutes <= 5) return "just now" else return "${minutesBetween.minutes} minutes ago"
                in 1..24 -> return "${hoursBetween.hours} hours ago"
            }
            1 -> return "yesterday"
            in 1..9999 -> "${queryDate.dayOfMonth()} ${queryDate.monthOfYear()}"
        }
        return ""
    }

}