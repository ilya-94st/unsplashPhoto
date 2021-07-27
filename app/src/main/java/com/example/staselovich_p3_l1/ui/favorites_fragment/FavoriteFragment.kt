package com.example.staselovich_p3_l1.ui.favorites_fragment

import android.os.Bundle
import android.view.View
import com.example.staselovich_p3_l1.R
import com.example.staselovich_p3_l1.adapter.MyPagerAdapter
import com.example.staselovich_p3_l1.base.BaseFragment
import com.example.staselovich_p3_l1.databinding.FragmentFavoriteBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {
    lateinit var tabLayout: TabLayout
    override fun getBinding() = R.layout.fragment_favorite
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility =
            View.VISIBLE
        setUpTabs()
    }
    private fun setUpTabs() {
        val adapter = MyPagerAdapter(childFragmentManager)
        tabLayout = binding.favoritesTabLayout
        adapter.addFragment(FavoritesImageFragment())
        adapter.addFragment(FavoritesQueriesFragment())
        binding.viewPager2.adapter = adapter
        tabLayout.setupWithViewPager(binding.viewPager2)

        tabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_image_24)
        tabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_bookmark_24)
    }
}