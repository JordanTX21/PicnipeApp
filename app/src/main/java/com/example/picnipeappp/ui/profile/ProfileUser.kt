package com.example.picnipeappp.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.picnipeappp.R
import com.example.picnipeappp.databinding.ActivityProfileUserBinding
import com.example.picnipeappp.ui.dashboard.components.uploads.UploadAdapter
import com.example.picnipeappp.ui.post.Post
import com.example.picnipeappp.ui.post.PostActivity

class ProfileUser : AppCompatActivity() {
    private lateinit var binding: ActivityProfileUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_user)

        binding = ActivityProfileUserBinding.inflate(layoutInflater)

        initRecycleView()
    }
    fun initRecycleView() {
        val postList = ArrayList<Post>()
        postList.add(
            Post(
                "id",
                "raaaaaa",
                "https://pbs.twimg.com/media/EjKz0c0WsAQWJwK.jpg",
                "mi titulo",
                "una cosa"
            )
        )
        postList.add(
            Post(
                "id",
                "raaaaaa",
                "https://pbs.twimg.com/media/EjKz0c0WsAQWJwK.jpg",
                "mi titulo",
                "una cosa"
            )
        )
        postList.add(
            Post(
                "id",
                "raaaaaa",
                "https://pbs.twimg.com/media/EjKz0c0WsAQWJwK.jpg",
                "mi titulo",
                "una cosa"
            )
        )
        val recycleView = binding.recyclerviewUserPostsProfile
        recycleView.layoutManager = GridLayoutManager(this,3)
        recycleView.adapter = UploadAdapter(postList){post -> onItemSelected(post)}
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