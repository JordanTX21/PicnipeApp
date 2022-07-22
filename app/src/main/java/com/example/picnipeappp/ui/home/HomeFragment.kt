package com.example.picnipeappp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.picnipeappp.databinding.FragmentHomeBinding
import com.example.picnipeappp.ui.components.AddPostDialogFragment
import com.example.picnipeappp.ui.components.SelectImageDialogFragment
import com.example.picnipeappp.ui.home.adapter.PostAdapter
import com.example.picnipeappp.ui.post.PostActivity


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            initRecyclerView()
            val addPost: View = binding.addPost
            addPost.setOnClickListener {
                initFloatingActionButton()
            }
        })


        return root;
    }

    fun initRecyclerView() {
        val recyclerview = binding.rvPost
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerview.adapter = PostAdapter(PostProvider.postList) { post -> onItemSelected(post) }
    }

    fun initFloatingActionButton(){
        val dialog = SelectImageDialogFragment()
        val fragmentManager = (activity as FragmentActivity).supportFragmentManager
        dialog.show(fragmentManager,"selectImage")
//        addPost.setOnClickListener { view ->
//            Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                .setAction("Action", null)
//                .show()
//        }
    }

    fun onItemSelected(postModel: Post) {
//        Toast.makeText(context, postModel.content, Toast.LENGTH_SHORT).show()
        val intent = Intent(getActivity(), PostActivity::class.java)
        intent.putExtra("post_id", postModel.id)
        intent.putExtra("post_photo", postModel.photo)
        intent.putExtra("post_title", postModel.title)
        intent.putExtra("post_content", postModel.content)
        getActivity()?.startActivity(intent)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}