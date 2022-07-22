package com.example.picnipeappp.ui.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.picnipeappp.R
import com.example.picnipeappp.databinding.ActivityPostBinding
import com.example.picnipeappp.ui.components.comments.Comment
import com.example.picnipeappp.ui.components.comments.CommentAdapter
import com.example.picnipeappp.ui.components.comments.CommentProvider
import com.example.picnipeappp.ui.login.UserSingleton
import com.example.picnipeappp.ui.notifications.NotificationProvider
import com.example.picnipeappp.ui.notifications.adapter.NotificationAdapter
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        binding = ActivityPostBinding.inflate(layoutInflater)

        val post_id = intent.getStringExtra("post_id")
        val post_photo = intent.getStringExtra("post_photo")
        val post_title = intent.getStringExtra("post_title")
        val post_content = intent.getStringExtra("post_content")
        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        val favPost = findViewById<FloatingActionButton>(R.id.favPost)

        var is_fav = false

        favPost.setOnClickListener {
            if (is_fav) {
                favPost.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                Toast.makeText(this, "Ya no es favorito", Toast.LENGTH_SHORT).show()
            } else {
                favPost.setImageResource(R.drawable.ic_baseline_favorite_24)
                Toast.makeText(this, "Favorito!", Toast.LENGTH_SHORT).show()
            }
            is_fav = !is_fav
        }

        topAppBar.setNavigationOnClickListener {
            finish()
        }

        userDescription.text = UserSingleton.username
//
        Glide.with(this).load(post_photo).into(imgPost)
        Glide.with(this).load(post_photo).into(userAvatarView)

        val provider = CommentProvider.commentsList
        val comments = listOf<Int>(1,2,3,4)

        for (comment in comments){
            provider.add(Comment(
                "Jordan",
                "https://pbs.twimg.com/media/EjKz0c0WsAQWJwK.jpg",
                "Me parece una mierda tu foto",
            ))
        }

        val btnSendComment = findViewById<Button>(R.id.btnSendComment)

        btnSendComment.setOnClickListener{
//            Toast.makeText(this, "Comentando", Toast.LENGTH_SHORT).show()
            val comment = tiComment.text.toString().trim()
            if(comment == ""){
                Toast.makeText(this, "Ingrese un comentario", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val newComment = Comment(
                UserSingleton.username.toString(),
                UserSingleton.photoPerfil.toString(),
                comment,
            )
            tiComment.text?.clear()
            CommentProvider.commentsList.add(newComment)
            initRecyclerView()

            //AQUI VA TU CODIGO
        }

        initRecyclerView()

    }

    fun initRecyclerView() {
//        Toast.makeText(this, CommentProvider.commentsList.size, Toast.LENGTH_SHORT).show()
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerviewComments)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = CommentAdapter(CommentProvider.commentsList)

    }
}