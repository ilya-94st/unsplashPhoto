package com.example.staselovich_p3_l1.adapter
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.staselovich_p3_l1.R
import com.example.staselovich_p3_l1.dataBase.EntyQuery
import com.example.staselovich_p3_l1.databinding.RecyclerHistoryBinding
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.Hours
import org.joda.time.Minutes
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

class HistaroryAdapter(
    private val listener: ChangeQueryState,
    private val onItemClick: OnItemClick
) :
    ListAdapter<EntyQuery, HistaroryAdapter.MViewHolder>(HISTORY_COMPARATOR) {

    inner class MViewHolder(private val binding: RecyclerHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.imageHeard.setOnClickListener {
                val photoPosition = bindingAdapterPosition
                if (photoPosition != RecyclerView.NO_POSITION) {
                    val item = getItem(photoPosition)
                    if (item != null) {
                        listener.changeQueryState(item)
                    }
                }
            }
            binding.root.setOnClickListener {
                val photoPosition = bindingAdapterPosition
                if (photoPosition != RecyclerView.NO_POSITION) {
                    val item = getItem(photoPosition)
                    if (item != null) {
                        onItemClick.openSearchPage(item.queryText)
                    }
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(query: EntyQuery) {
            binding.country.text = query.queryText
            binding.progres.text = "${query.total} results,"
            binding.time.text = compareDates(query.date)
            if (query.liked) {
                binding.imageHeard.setColorFilter(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.blue
                    )
                )
            } else {
                binding.imageHeard.setColorFilter(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.black
                    )
                )
            }
        }
    }

    companion object {
        private val HISTORY_COMPARATOR = object : DiffUtil.ItemCallback<EntyQuery>() {
            override fun areItemsTheSame(oldItem: EntyQuery, newItem: EntyQuery) =
                oldItem.queryText == newItem.queryText

            override fun areContentsTheSame(oldItem: EntyQuery, newItem: EntyQuery) =
                oldItem == newItem
        }
    }

    interface ChangeQueryState {
        fun changeQueryState(query: EntyQuery)
    }

    interface OnItemClick {
        fun openSearchPage(query: String)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
        return MViewHolder(
            RecyclerHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }
}