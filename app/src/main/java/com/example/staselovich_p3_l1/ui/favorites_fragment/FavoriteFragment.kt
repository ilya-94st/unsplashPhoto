package com.example.staselovich_p3_l1.ui.favorites_fragment

import android.os.Bundle
import android.view.View
import com.example.staselovich_p3_l1.R
import com.example.staselovich_p3_l1.adapter.MyPagerAdapter
import com.example.staselovich_p3_l1.base.BaseFragment
import com.example.staselovich_p3_l1.databinding.FragmentFavoriteBinding
import com.google.android.material.tabs.TabLayoutMediator


class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {
    override fun getBinding() = R.layout.fragment_favorite
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MyPagerAdapter(requireActivity().supportFragmentManager,lifecycle)
        binding.viewPager2.adapter =adapter
        TabLayoutMediator(binding.tableLayaut,binding.viewPager2){tab,position->
            when(position){
                0 -> {
               tab.setIcon(R.drawable.ic_baseline_image_24)
                }
                1 -> {
               tab.setIcon(R.drawable.ic_baseline_bookmark_24)
                }
            }
        }.attach()
    }
}