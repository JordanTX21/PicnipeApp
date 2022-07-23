package com.example.picnipeappp.ui.search

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.picnipeappp.R
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.picnipeappp.databinding.FragmentSearchBinding
import com.example.picnipeappp.ui.post.PostActivity
import com.example.picnipeappp.ui.profile.ProfileUser
import com.example.picnipeappp.ui.search.SearchViewModel
import com.example.picnipeappp.ui.search.adapter.SearchAdapter
import com.google.android.material.textfield.TextInputEditText

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
            initSearch(root)
        })
        return root
    }

    fun initRecyclerView(){
        val recyclerview = binding.recyclerviewSearch
        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.adapter = SearchAdapter(SearchProvider.searchList){search -> onItemClick(search)}
    }

    fun onItemClick(search: DinamicSearch) {
        if(search.type == "POST"){
            val intent = Intent(getActivity(), PostActivity::class.java)
            intent.putExtra("post_id", search.id)
            intent.putExtra("post_photo", search.photo)
            intent.putExtra("post_title", search.title)
            intent.putExtra("post_content", search.detail)
            intent.putExtra("post_creator", search.iduser)
            getActivity()?.startActivity(intent)
        }else if(search.type == "USER"){

            val intent = Intent(getActivity(), ProfileUser::class.java)
            intent.putExtra("user_id", search.id)
            intent.putExtra("user_photo", search.photo)
            intent.putExtra("user_name", search.title)
            intent.putExtra("user_description", search.detail)
            getActivity()?.startActivity(intent)
        }
    }

    fun initSearch(root:View){
        val btnSendSearch = root.findViewById<Button>(R.id.btnSendSearch)
        btnSendSearch.setOnClickListener{
            val itSearch = root.findViewById<TextInputEditText>(R.id.itSearch)
            val search = itSearch.text.toString().trim()
            if(search == ""){
                Toast.makeText(context, "Ingrese una busqueda válida", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(context, search , Toast.LENGTH_SHORT).show()
            itSearch.text?.clear()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}