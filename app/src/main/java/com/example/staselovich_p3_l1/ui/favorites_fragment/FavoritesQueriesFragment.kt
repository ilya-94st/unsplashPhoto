package com.example.staselovich_p3_l1.ui.favorites_fragment


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.staselovich_p3_l1.R
import com.example.staselovich_p3_l1.adapter.FavoritesQueriesAdapter
import com.example.staselovich_p3_l1.dataBase.EntyQuery
import com.example.staselovich_p3_l1.databinding.FragmentFavoritesQueriseBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesQueriesFragment : Fragment(R.layout.fragment_favorites_querise), FavoritesQueriesAdapter.OnItemClick, FavoritesQueriesAdapter.ChangeQueryState {
    private var _binding: FragmentFavoritesQueriseBinding? = null
    private val binding get() = _binding!!
    private val mImageSelect by viewModels<FavoritesQueriesViewModell>()
    private lateinit var adapter: FavoritesQueriesAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavoritesQueriseBinding.bind(view)
        initAdapter()
        mImageSelect.allEntyQuery.observe(viewLifecycleOwner,{
            adapter.submitList(it)
        })
    }
    fun initAdapter() {
        adapter = FavoritesQueriesAdapter(this,this)
        val recyclerHistory = binding.recyclelImageSelect
        recyclerHistory.setHasFixedSize(true)
        recyclerHistory.adapter = adapter
    }
    @DelicateCoroutinesApi


    override fun changeQueryState(query: EntyQuery) {
        GlobalScope.launch {
            if (query.liked) {
                mImageSelect.changeQueryLikeState(
                    EntyQuery(
                        query.queryText,
                        false,
                        query.total,
                        query.date
                    )
                )
            } else {
                mImageSelect.changeQueryLikeState(
                    EntyQuery(
                        query.queryText,
                        true,
                        query.total,
                        query.date
                    )
                )
            }
        }
    }

    override fun openSearchPage(query: String) {
        val action = FavoriteFragmentDirections.actionSelectFragment2ToSeachFragment(query)
        findNavController().navigate(action)
    }
}