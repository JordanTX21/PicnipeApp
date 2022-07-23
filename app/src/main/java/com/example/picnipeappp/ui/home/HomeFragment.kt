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
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.util.*
import kotlin.collections.HashMap


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    lateinit var storageReference: StorageReference
    lateinit var mauth : FirebaseAuth
    lateinit var authorUid : String
    private var firestore = FirebaseFirestore.getInstance()
    var dowloadImgUid : String = ""


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
        authorUid = mauth.uid.toString()

        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel.text.observe(viewLifecycleOwner, Observer {

            firestore.collection("publications").get().addOnSuccessListener { post ->
                var provider = PostProvider.postList
                provider.clear()
                for(pos in post){
                    provider.add(
                        Post(
                            pos.id,
                            pos.get("iduserCreator").toString(),
                            pos.get("image").toString(),
                            pos.get("title").toString(),
                            pos.get("content").toString(),
                        )
                    )

                }
                initRecyclerView()
            }

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
        // Subimos el archivo al storage
        val reference: StorageReference = storageReference.child("publication").child(authorUid).child(img.toString())
        if (img != null) {
            reference.putFile(img).addOnSuccessListener {
                ObtainUrlImg(title, content , authorUid, reference)
                Toast.makeText(context, "foto cargada", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(context, "error al cargar la foto", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun ObtainUrlImg(titulo: String , contenido : String , iduser : String ,imagRef : StorageReference){
        imagRef.downloadUrl.addOnSuccessListener ( OnSuccessListener<Uri> { uri ->
            dowloadImgUid = uri.toString()
                var data = hashMapOf(
                    "title" to contenido,
                    "content" to titulo,
                    "iduserCreator" to iduser,
                    "image" to dowloadImgUid
                )
                firestore.collection("publications").add(data).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(context, "guardado exitoso" , Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context, "error al ingreesar en la bd" , Toast.LENGTH_SHORT).show()
                    }
                }
        }
        )
    }

    fun onItemSelected(postModel: Post) {
        val intent = Intent(getActivity(), PostActivity::class.java)
        intent.putExtra("post_id", postModel.id)
        intent.putExtra("post_photo", postModel.photo)
        intent.putExtra("post_title", postModel.title)
        intent.putExtra("post_content", postModel.content)
        intent.putExtra("post_creator", postModel.iduser)
        getActivity()?.startActivity(intent)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private fun <E> List<E>.clear() {
    TODO("Not yet implemented")
}
