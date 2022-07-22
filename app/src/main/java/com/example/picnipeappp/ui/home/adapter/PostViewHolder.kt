package com.example.picnipeappp.ui.home.adapter

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.picnipeappp.databinding.ItemPostBinding
import com.example.picnipeappp.ui.home.Post
import com.example.picnipeappp.ui.post.PostActivity

class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = ItemPostBinding.bind(view)
    var context = view.context
    fun render(postModel: Post,onClickListener:(Post) -> Unit) {
        Glide.with(binding.ivPost.context).load(postModel.photo)
            .into(binding.ivPost)
        binding.titlePost.text = postModel.title



        binding.ivPost.setOnClickListener {
            onClickListener(postModel)
        }
    }
}