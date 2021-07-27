package com.example.staselovich_p3_l1.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.staselovich_p3_l1.ui.favorites_fragment.FavoritesImageFragment
import com.example.staselovich_p3_l1.ui.favorites_fragment.FavoritesQueriesFragment

class MyPagerAdapter(fm:FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
   private val fragmentList = ArrayList<Fragment>()

    override fun getCount(): Int {
       return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
       return fragmentList[position]
    }
    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
    }
}