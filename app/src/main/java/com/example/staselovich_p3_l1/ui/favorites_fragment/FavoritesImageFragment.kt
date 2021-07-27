package com.example.staselovich_p3_l1.ui.favorites_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.staselovich_p3_l1.R
import com.example.staselovich_p3_l1.adapter.FavoritesImagesAdapter
import com.example.staselovich_p3_l1.dataBase.EntyImage
import com.example.staselovich_p3_l1.databinding.FragmentFavoritesImagesBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesImageFragment : Fragment(R.layout.fragment_favorites_images),
    FavoritesImagesAdapter.OnItemClick, FavoritesImagesAdapter.OnDeleteClick {
    private var _binding: FragmentFavoritesImagesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<FavoritesImageViewModel>()
    private lateinit var adapter: FavoritesImagesAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavoritesImagesBinding.bind(view)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.VISIBLE
        adapter = FavoritesImagesAdapter(this,this)
        binding.recyclerSelect.adapter =adapter
        viewModel.allFavoritePhotos.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    @DelicateCoroutinesApi
    override fun onItemClick(photo: Any) {
        GlobalScope.launch(Dispatchers.Main) {
            viewModel.getPhotoById(adapter.clickedItemId)
            val action =
                FavoriteFragmentDirections.actionSelectFragment2ToDetailsFragment(viewModel.photo[0])
            findNavController().navigate(action)
        }
    }

    @DelicateCoroutinesApi
    override fun onDeleteClick(photo: EntyImage) {
        GlobalScope.launch {
            viewModel.delteImages(photo)
        }
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }
}
