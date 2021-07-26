package com.example.staselovich_p3_l1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.staselovich_p3_l1.R
import com.example.staselovich_p3_l1.databinding.RecyclerWallBinding
import com.example.staselovich_p3_l1.model.UnsplashPhoto

class UnsplashAdapter(private val listener: OnItemClickListener): PagingDataAdapter<UnsplashPhoto, UnsplashAdapter.PagingViewHolder>(
    PHOTO_COMPARATOR) {
    inner class PagingViewHolder(private val binding: RecyclerWallBinding): RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(photo: UnsplashPhoto) {
    binding.apply {
        Glide.with(itemView)
            .load(photo.urls.regular)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.ic_baseline_error_24)
            .into(imageView)
    }
}

    }

    interface OnItemClickListener {
        fun onItemClick(photo: UnsplashPhoto)
    }

    companion object{
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<UnsplashPhoto>(){
            override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto) =
                oldItem.id == newItem.id
            override fun areContentsTheSame(
                oldItem: UnsplashPhoto,
                newItem: UnsplashPhoto
            ) = oldItem.id == newItem.id

        }
    }

    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
       val currentItem = getItem(position)
        if (currentItem!=null) {
            holder.bind(currentItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
       val binding = RecyclerWallBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PagingViewHolder(binding)
    }
}