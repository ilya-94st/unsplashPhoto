package com.example.staselovich_p3_l1.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.staselovich_p3_l1.dataBase.EntyImage
import com.example.staselovich_p3_l1.databinding.RecyclerFavoriteImagesBinding


class FavoritesImagesAdapter(
    private val listener: OnItemClick,
    private val deleteListener: OnDeleteClick
) :
    ListAdapter<EntyImage, FavoritesImagesAdapter.MViewHolder>(PHOTO_COMPARATOR) {

    var clickedItemId = ""

    inner class MViewHolder(private val binding: RecyclerFavoriteImagesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val photoPosition = bindingAdapterPosition
                if (photoPosition != RecyclerView.NO_POSITION) {
                    val item = getItem(photoPosition)
                    if (item != null) {
                        clickedItemId = item.imgId
                        listener.onItemClick(item)
                    }
                }
            }
            binding.imageDelete.setOnClickListener {
                val photoPosition = bindingAdapterPosition
                if (photoPosition != RecyclerView.NO_POSITION) {
                    val item = getItem(photoPosition)
                    if (item != null) {
                        deleteListener.onDeleteClick(item)
                    }
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(img: EntyImage) {
            Glide.with(itemView)
                .load(img.image)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imageView)
        }
    }

    interface OnItemClick {
        fun onItemClick(photo: Any)
    }

    interface OnDeleteClick {
        fun onDeleteClick(photo: EntyImage)
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<EntyImage>() {
            override fun areItemsTheSame(oldItem: EntyImage, newItem: EntyImage) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: EntyImage, newItem: EntyImage) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
        return MViewHolder(
            RecyclerFavoriteImagesBinding.inflate(
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
