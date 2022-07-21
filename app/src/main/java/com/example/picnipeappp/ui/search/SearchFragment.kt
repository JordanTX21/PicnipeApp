package com.example.picnipeappp.ui.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.picnipeappp.R
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.picnipeappp.databinding.FragmentSearchBinding
import com.example.picnipeappp.ui.search.SearchViewModel
import com.example.picnipeappp.ui.search.adapter.SearchAdapter

class SearchFragment : Fragment() {
    private lateinit var searchViewModel: SearchViewModel
    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textSearch
        searchViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
            initRecyclerView()
        })
        return root
    }

    fun initRecyclerView(){
        val recyclerview = binding.recyclerviewSearch
        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.adapter = SearchAdapter(SearchProvider.searchList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}