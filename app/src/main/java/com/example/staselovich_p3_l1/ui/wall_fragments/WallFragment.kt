package com.example.staselovich_p3_l1.ui.wall_fragments


import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.staselovich_p3_l1.R
import com.example.staselovich_p3_l1.adapter.UnsplashAdapter
import com.example.staselovich_p3_l1.adapter.UnsplashPhotoLoadAdapter
import com.example.staselovich_p3_l1.databinding.FragmentWallBinding
import com.example.staselovich_p3_l1.model.UnsplashPhoto
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class WallFragment : Fragment(R.layout.fragment_wall), UnsplashAdapter.OnItemClickListener {
    private var _binding: FragmentWallBinding? = null
    var timer: Timer? =null

    private val binding get() = _binding!!
    private val viewModel by viewModels<WallViewModel>()
    private val args by navArgs<WallFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWallBinding.bind(view)
        if(!args.link.isEmpty()){
            viewModel.searcPhotos(args.link)
        }
        val adapter = UnsplashAdapter(this)
        binding.apply {
            recyclerSearch.setHasFixedSize(true)
            recyclerSearch.adapter = adapter.withLoadStateHeaderAndFooter(
                header = UnsplashPhotoLoadAdapter{adapter.retry()},
                footer = UnsplashPhotoLoadAdapter{adapter.retry()},
            )
        }
        viewModel.photos.observe(viewLifecycleOwner) {
adapter.submitData(viewLifecycleOwner.lifecycle,it)
        }
        binding.buttonRetry.setOnClickListener {
            adapter.retry()
        }

        adapter.addLoadStateListener { loadState->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerSearch.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error
                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached &&
                        adapter.itemCount < 1) {
                    recyclerSearch.isVisible = false
                    textViewError.isVisible = true
                } else {
                    textViewError.isVisible = false
                }
            }
        }

        binding.searchView2.addTextChangedListener(searchTextWatcher)


        binding.buttonFrame.setOnClickListener {
            if( (binding.recyclerSearch.layoutManager as GridLayoutManager).spanCount == 2){
                (binding.recyclerSearch.layoutManager as GridLayoutManager).spanCount = 3
                binding.buttonFrame.setImageResource(R.drawable.ic_view_module)
            } else{
                (binding.recyclerSearch.layoutManager as GridLayoutManager).spanCount = 2
                binding.buttonFrame.setImageResource(R.drawable.ic_view_module2)
            }
       }
    }
    private val searchTextWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(arg0: Editable) {
            if (!binding.searchView2.text.isNullOrEmpty()) {
                timer = Timer()
                timer?.schedule(object : TimerTask() {
                    override fun run() {
                        Handler(Looper.getMainLooper()).postDelayed({
                            viewModel.searcPhotos(binding.searchView2.text.toString())
                            viewModel.addQuery()
                            binding.searchView2.hideKeyboard()
                        }, 0)
                    }
                }, 1500)
            }
        }
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (timer != null) {
                timer?.cancel()
            }
        }
    }



    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onItemClick(photo: UnsplashPhoto) {
       val action = WallFragmentDirections.actionSeachFragmentToDetailsFragment(photo)
        findNavController().navigate(action)
    }
}