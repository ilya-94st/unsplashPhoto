package com.example.staselovich_p3_l1

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.staselovich_p3_l1.base.BaseFragment
import com.example.staselovich_p3_l1.databinding.FragmentLoandingBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LoandingFragment : BaseFragment<FragmentLoandingBinding>() {
    override fun getBinding() = R.layout.fragment_loanding

    @DelicateCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.INVISIBLE
        binding.imageBook.animate().translationY(-2500f).setDuration(1500).setStartDelay(1000)
        binding.textWallpapers.animate().translationY(2000f).setDuration(1500).setStartDelay(1000)
        GlobalScope.launch {
            delay(2200)
            getActivity()?.runOnUiThread {
                val direction = LoandingFragmentDirections.actionLoandingFragmentToSeachFragment("")
                findNavController().navigate(direction)
                requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.VISIBLE
            }
        }
    }
}