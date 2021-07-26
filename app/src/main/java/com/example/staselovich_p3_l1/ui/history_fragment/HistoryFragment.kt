package com.example.staselovich_p3_l1.ui.history_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.staselovich_p3_l1.R
import com.example.staselovich_p3_l1.adapter.HistaroryAdapter
import com.example.staselovich_p3_l1.dataBase.EntyQuery
import com.example.staselovich_p3_l1.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment : Fragment(R.layout.fragment_history), HistaroryAdapter.OnItemClick, HistaroryAdapter.ChangeQueryState {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val mhistoryScreen by viewModels<ViewModelHistory>()
    private lateinit var adapter: HistaroryAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHistoryBinding.bind(view)
        mhistoryScreen.allEntyQuery.observe(viewLifecycleOwner,{
            adapter.submitList(it)
        })
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = HistaroryAdapter(this, this)
        val recyclerView = binding.recyclelHistory
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }

    @DelicateCoroutinesApi
    override fun changeQueryState(query: EntyQuery) {
        GlobalScope.launch {
            if (query.liked) {
                mhistoryScreen.changeQueryLikeState(EntyQuery(query.queryText, false, query.total, query.date))
            } else {
                mhistoryScreen.changeQueryLikeState(EntyQuery(query.queryText, true, query.total, query.date))
            }
        }
    }

    override fun openSearchPage(query: String) {
        val action = HistoryFragmentDirections.actionHistoryFragmentToSeachFragment(query)
        findNavController().navigate(action)
    }
}