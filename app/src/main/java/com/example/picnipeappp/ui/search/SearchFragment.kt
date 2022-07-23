package com.example.picnipeappp.ui.search

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.picnipeappp.R
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.picnipeappp.databinding.FragmentSearchBinding
import com.example.picnipeappp.ui.post.PostActivity
import com.example.picnipeappp.ui.profile.ProfileUser
import com.example.picnipeappp.ui.search.adapter.SearchAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore

class SearchFragment : Fragment() {
    private lateinit var searchViewModel: SearchViewModel
    private var _binding: FragmentSearchBinding? = null
    private val busquedaUser = FirebaseFirestore.getInstance()
    private val busquedaPost = FirebaseFirestore.getInstance()

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

    fun initRecyclerView() {
        val recyclerview = binding.recyclerviewSearch
        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.adapter =
            SearchAdapter(SearchProvider.searchList) { search -> onItemClick(search) }
    }

    fun onItemClick(search: DinamicSearch) {
        if (search.type == "POST") {
            val intent = Intent(getActivity(), PostActivity::class.java)
            intent.putExtra("post_id", search.id)
            intent.putExtra("post_photo", search.photo)
            intent.putExtra("post_title", search.title)
            intent.putExtra("post_content", search.detail)
            intent.putExtra("post_creator", search.iduser)
            getActivity()?.startActivity(intent)
        } else if (search.type == "USER") {

            val intent = Intent(getActivity(), ProfileUser::class.java)
            intent.putExtra("user_id", search.id)
            intent.putExtra("user_photo", search.photo)
            intent.putExtra("user_name", search.title)
            intent.putExtra("user_description", search.detail)
            getActivity()?.startActivity(intent)
        }
    }

    fun initSearch(root: View) {
        val btnSendSearch = root.findViewById<Button>(R.id.btnSendSearch)
        btnSendSearch.setOnClickListener {
            val itSearch = root.findViewById<TextInputEditText>(R.id.itSearch)
            val search = itSearch.text.toString().trim()
            if (search == "") {
                Toast.makeText(context, "Ingrese una busqueda válida", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                binding.progressBarSearch.visibility = View.VISIBLE
                val provider = SearchProvider.searchList
                provider.clear()

                busquedaUser.collection("users").get().addOnSuccessListener { usersfoud ->
                    for (userfound in usersfoud) {
                        if (userfound.get("Nombre").toString().contains(search)) {
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
                    binding.progressBarSearch.visibility = View.GONE
                    initRecyclerView()
                    itSearch.text?.clear()
                }
                busquedaPost.collection("publications").get().addOnSuccessListener { postsfoud ->
                    for (postfoud in postsfoud) {
                        if (postfoud.get("title").toString().contains(search)) {
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
                    binding.progressBarSearch.visibility = View.GONE
                    initRecyclerView()
                    itSearch.text?.clear()
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}