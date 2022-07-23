package com.example.picnipeappp.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.picnipeappp.BuildConfig
import com.example.picnipeappp.databinding.FragmentHomeBinding
import com.example.picnipeappp.ui.components.AddPostDialogFragment
import com.example.picnipeappp.ui.components.SelectImageDialogFragment
import com.example.picnipeappp.ui.post.Post
import com.example.picnipeappp.ui.post.adapter.PostAdapter
import com.example.picnipeappp.ui.post.PostActivity
import com.example.picnipeappp.ui.post.PostProvider
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.io.File


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
                val provider = PostProvider.postList
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
                binding.progressBarHome.visibility = View.GONE
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
//        val recyclerview = binding.rvPost
        binding.rvPost.setHasFixedSize(true)
        binding.rvPost.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvPost.adapter = PostAdapter(PostProvider.postList) { post -> onItemSelected(post) }
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
            openCamera.launch(intent)
        }
    }

    val openCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK){
                 val data = result.data!!
                val bitmap = data.extras!!.get("data") as Bitmap
                val uri: Uri? = null

                // Handle the Intent
                val dialog = AddPostDialogFragment(uri,bitmap) { img, img2, title, content ->
                    onAceptedDialog(
                        img,
                        img2,
                        title,
                        content
                    )
                }
                val fragmentManager = (activity as FragmentActivity).supportFragmentManager
                dialog.show(fragmentManager, "createPost")
            }
        }

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data?.data

                // Handle the Intent
                val dialog = AddPostDialogFragment(data) { img, img2, title, content ->
                    onAceptedDialog(
                        img,
                        img2,
                        title,
                        content
                    )
                }
                val fragmentManager = (activity as FragmentActivity).supportFragmentManager
                dialog.show(fragmentManager, "createPost")
            }
        }

    private lateinit var file: File
    private fun createPhotoFile(){
        val dir = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        file = File.createTempFile("IMG_${System.currentTimeMillis()}_",".jpg",dir)
    }

    fun onAceptedDialog(img: Uri?,img2: Bitmap?, title: String, content: String) {
        // Subimos el archivo al storage
        val reference: StorageReference = storageReference.child("publication").child(authorUid).child(img.toString())
        if (img != null) {
            reference.putFile(img).addOnSuccessListener {
                ObtainUrlImg(title, content , authorUid, reference)
            }.addOnFailureListener {
                Toast.makeText(context, "Error al subir archivo", Toast.LENGTH_SHORT).show()
            }
        }else if (img2 != null){
            reference.putFile(getImageUriFromBitmap(context!!,img2)).addOnSuccessListener {
                ObtainUrlImg(title, content , authorUid, reference)
            }.addOnFailureListener {
                Toast.makeText(context, "Error al subir archivo", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun getImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri{
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path.toString())
    }


    fun ObtainUrlImg(titulo: String , contenido : String , iduser : String ,imagRef : StorageReference){
        imagRef.downloadUrl.addOnSuccessListener ( OnSuccessListener<Uri> { uri ->
            dowloadImgUid = uri.toString()
                val data = hashMapOf(
                    "title" to contenido,
                    "content" to titulo,
                    "iduserCreator" to iduser,
                    "image" to dowloadImgUid
                )
                firestore.collection("publications").add(data).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(context, "Publicacion creada" , Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context, "Error al subir publicación" , Toast.LENGTH_SHORT).show()
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
