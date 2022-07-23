package com.example.picnipeappp.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.picnipeappp.R
import com.example.picnipeappp.databinding.ActivityProfileUserBinding
import com.example.picnipeappp.ui.dashboard.components.uploads.UploadAdapter
import com.example.picnipeappp.ui.login.UserSingleton
import com.example.picnipeappp.ui.post.Post
import com.example.picnipeappp.ui.post.PostActivity
import com.example.picnipeappp.ui.post.PostProvider
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.firestore.FirebaseFirestore
import io.getstream.avatarview.AvatarView
import io.getstream.avatarview.coil.loadImage

class ProfileUser : AppCompatActivity() {
    private lateinit var binding: ActivityProfileUserBinding
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_user)

        binding = ActivityProfileUserBinding.inflate(layoutInflater)

        val user_id = intent.getStringExtra("user_id")
        val user_photo = intent.getStringExtra("user_photo")
        val user_name = intent.getStringExtra("user_name")
        val user_description = intent.getStringExtra("user_description")
        val topAppBarProfileUser = findViewById<MaterialToolbar>(R.id.topAppBarProfileUser)

        val userNameProfile = findViewById<TextView>(R.id.userNameProfile)
        val descriptionUserProfile = findViewById<TextView>(R.id.descriptionUserProfile)
        val avUserProfile = findViewById<AvatarView>(R.id.avUserProfile)

        userNameProfile.text = user_name
        descriptionUserProfile.text = user_description
        avUserProfile.loadImage(user_photo)
        topAppBarProfileUser.title = "Perfil de Usuario"
        setSupportActionBar(topAppBarProfileUser)
        topAppBarProfileUser.setNavigationOnClickListener {
            finish()
        }

        firestore.collection("publications").whereEqualTo("iduserCreator", user_id)
            .get()
            .addOnSuccessListener { post ->
                val provider = PostProvider.postList
                provider.clear()
                for (pos in post) {
                    provider.add(
                        Post(
                            pos.id,
                            pos.get("iduserCreator").toString(),
                            pos.get("image").toString(),
                            pos.get("image").toString(),
                            pos.get("content").toString(),
                        )
                    )
                }
                initRecycleView()
            }
    }
    fun initRecycleView() {
        val recycleView = findViewById<RecyclerView>(R.id.recyclerviewUserPostsProfile)
        recycleView.layoutManager = GridLayoutManager(this,3)
        recycleView.adapter = UploadAdapter(PostProvider.postList){post -> onItemSelected(post)}
    }
    fun onItemSelected(post: Post){
        val intent = Intent(this, PostActivity::class.java)
        intent.putExtra("post_id", post.id)
        intent.putExtra("post_photo", post.photo)
        intent.putExtra("post_title", post.title)
        intent.putExtra("post_content", post.content)
        intent.putExtra("post_creator", post.iduser)
        startActivity(intent)
    }
}