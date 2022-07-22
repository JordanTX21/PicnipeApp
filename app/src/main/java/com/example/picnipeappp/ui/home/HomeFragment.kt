package com.example.picnipeappp.ui.home

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.picnipeappp.databinding.FragmentHomeBinding
import com.example.picnipeappp.ui.components.AddPostDialogFragment
import com.example.picnipeappp.ui.components.SelectImageDialogFragment
import com.example.picnipeappp.ui.home.adapter.PostAdapter
import com.example.picnipeappp.ui.login.UserSingleton
import com.example.picnipeappp.ui.post.PostActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    lateinit var storageReference: StorageReference
    val storagePath = "publication/"
    lateinit var mauth : FirebaseAuth

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        storageReference = FirebaseStorage.getInstance().getReference()
        mauth = FirebaseAuth.getInstance()
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

    fun initFloatingActionButton() {
        val dialog = SelectImageDialogFragment() { data -> onOptionSelected(data) }
        val fragmentManager = (activity as FragmentActivity).supportFragmentManager
        dialog.show(fragmentManager, "selectImage")
    }

    fun onOptionSelected(option: Int) {

        if (option == 0) {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startForResult.launch(intent)
        } else if (option == 1) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE)
            startForResult.launch(intent)
        }
    }

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data?.data

                // Handle the Intent
                val dialog = AddPostDialogFragment(data) { img, title, content ->
                    onAceptedDialog(
                        img,
                        title,
                        content
                    )
                }
                val fragmentManager = (activity as FragmentActivity).supportFragmentManager
                dialog.show(fragmentManager, "createPost")
            }
        }

    fun onAceptedDialog(img: Uri?, title: String, content: String) {
        val ruteStoragePhoto: String = storagePath + "${mauth.uid}/" + "${img.toString()}"
        val reference: StorageReference = storageReference.child(ruteStoragePhoto)
        if (img != null) {
            reference.putFile(img).addOnSuccessListener {
                Toast.makeText(context, "Archivo subido Exitosamente", Toast.LENGTH_SHORT)
                    .show()
            }.addOnFailureListener {
                Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun onItemSelected(postModel: Post) {
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