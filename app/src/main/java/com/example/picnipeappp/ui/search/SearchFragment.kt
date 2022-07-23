package com.example.picnipeappp.ui.search

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
import com.example.picnipeappp.ui.components.comments.Comment
import com.example.picnipeappp.ui.components.comments.CommentProvider
import com.example.picnipeappp.ui.login.UserSingleton
import com.example.picnipeappp.ui.search.SearchViewModel
import com.example.picnipeappp.ui.search.adapter.SearchAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore

class SearchFragment : Fragment() {
    private lateinit var searchViewModel: SearchViewModel
    private var _binding: FragmentSearchBinding? = null
    private val busquedaUser= FirebaseFirestore.getInstance()
    private val busquedaPost= FirebaseFirestore.getInstance()
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
        recyclerview.adapter = SearchAdapter(SearchProvider.searchList)
    }

    fun initSearch(root:View){
        val btnSendSearch = root.findViewById<Button>(R.id.btnSendSearch)
        btnSendSearch.setOnClickListener{
            val itSearch = root.findViewById<TextInputEditText>(R.id.itSearch)
            val search = itSearch.text.toString().trim()
            if(search == ""){
                Toast.makeText(context, "Ingrese una busqueda válida", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else{
                val provider = SearchProvider.searchList
                provider.clear()

                busquedaUser.collection("users").get().addOnSuccessListener { usersfoud ->
                    for (userfound in usersfoud) {
                        if(userfound.get("Nombre").toString().contains(search)){
                            provider.add(
                                DinamicSearch(
                                    userfound.id,
                                    userfound.get("fotoPerfil").toString(),
                                    userfound.get("Nombre").toString(),
                                    userfound.get("Correo").toString(),
                                    userfound.id,
                                    "USER"
                                )
                            )
                        }
                    }
                }
                busquedaPost.collection("publications").get().addOnSuccessListener { postsfoud ->
                    for (postfoud in postsfoud) {
                        if(postfoud.get("title").toString().contains(search)){
                        provider.add(
                            DinamicSearch(
                                postfoud.id,
                                postfoud.get("image").toString(),
                                postfoud.get("title").toString(),
                                postfoud.get("content").toString(),
                                postfoud.get("iduserCreator").toString(),
                                "POST"
                            )
                        )
                        }
                    }
                }
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