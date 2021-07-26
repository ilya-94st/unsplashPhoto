package com.example.staselovich_p3_l1.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.staselovich_p3_l1.ui.favorites_fragment.FavoritesImageFragment
import com.example.staselovich_p3_l1.ui.favorites_fragment.FavoritesQueriesFragment

class MyPagerAdapter(fm:FragmentManager,lifecycle: Lifecycle): FragmentStateAdapter(fm,lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                FavoritesImageFragment()
            }
            else -> {
                FavoritesQueriesFragment()
            }
        }
    }
}