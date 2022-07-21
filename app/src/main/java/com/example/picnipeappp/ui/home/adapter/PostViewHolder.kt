package com.example.picnipeappp.ui.home.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.picnipeappp.R
import com.example.picnipeappp.databinding.ItemPostBinding
import com.example.picnipeappp.ui.home.Post
import com.makeramen.roundedimageview.RoundedImageView
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = ItemPostBinding.bind(view)

    fun render(postModel: Post) {
        Glide.with(binding.ivPost.context).load(postModel.photo)
            .into(binding.ivPost)
        binding.titlePost.text = postModel.title

        binding.ivPost.setOnClickListener {
            Toast.makeText(binding.ivPost.context, postModel.content, Toast.LENGTH_SHORT).show()
        }
    }
}