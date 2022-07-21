package com.example.picnipeappp.ui.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.picnipeappp.R
import com.example.picnipeappp.databinding.ActivityPostBinding
import com.example.picnipeappp.ui.login.UserSingleton
import io.getstream.avatarview.AvatarView
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

//        val imgPost = imgPost
//        val photoUser = userAvatarView
//        val descriptionUser = userDescription

        Toast.makeText(this, post_photo, Toast.LENGTH_SHORT).show()
        userDescription.text = UserSingleton.username
//
        Glide.with(this).load(post_photo).into(imgPost)
        Glide.with(this).load(post_photo).into(userAvatarView)

    }
}