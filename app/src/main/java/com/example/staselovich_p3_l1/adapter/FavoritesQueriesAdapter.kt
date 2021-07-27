package com.example.staselovich_p3_l1.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.staselovich_p3_l1.dataBase.EntyQuery
import com.example.staselovich_p3_l1.databinding.RecyclerFavoritesQueriesBinding
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.Hours
import org.joda.time.Minutes
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

class FavoritesQueriesAdapter(val listener: ChangeQueryState, val onItemClick: OnItemClick, private val compareDates: (date:String)-> String): ListAdapter<EntyQuery, FavoritesQueriesAdapter.MyHolder>(
    History_COMPARATOR
) {
    inner class MyHolder(val binding: RecyclerFavoritesQueriesBinding): RecyclerView.ViewHolder(binding.root){

        init {
            binding.sendDelete.setOnClickListener {
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
        fun bind(historyScreen: EntyQuery) {
            binding.country.text = historyScreen.queryText
            binding.progres.text = "${historyScreen.total} results,"
            binding.time.text = compareDates(historyScreen.date)
        }
    }



    interface ChangeQueryState {
        fun changeQueryState(query: EntyQuery)
    }

    interface OnItemClick {
        fun openSearchPage(query: String)
    }



    companion object {
        private val History_COMPARATOR = object : DiffUtil.ItemCallback<EntyQuery>() {
            override fun areItemsTheSame(oldItem: EntyQuery, newItem: EntyQuery) =
                oldItem.queryText == newItem.queryText

            override fun areContentsTheSame(oldItem: EntyQuery, newItem: EntyQuery) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(RecyclerFavoritesQueriesBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }


}