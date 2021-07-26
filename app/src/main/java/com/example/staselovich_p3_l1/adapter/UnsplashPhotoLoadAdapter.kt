package com.example.staselovich_p3_l1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.staselovich_p3_l1.databinding.UnplashPhotoLoadStateFooterBinding

class UnsplashPhotoLoadAdapter(val retry:()-> Unit): LoadStateAdapter<UnsplashPhotoLoadAdapter.LoadStateViewHolder>() {
   inner class LoadStateViewHolder(private val binding: UnplashPhotoLoadStateFooterBinding): RecyclerView.ViewHolder(binding.root){

        init {
            binding.buttonRetry.setOnClickListener {
                retry.invoke()
            }
        }
fun bind(loadState:LoadState){
binding.apply {
    progressBar.isVisible = loadState is LoadState.Loading
    buttonRetry.isVisible = loadState !is LoadState.Loading
    textNoInternet.isVisible = loadState !is LoadState.Loading
}
}
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = UnplashPhotoLoadStateFooterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LoadStateViewHolder(binding)
    }
}