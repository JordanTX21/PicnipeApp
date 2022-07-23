package com.example.picnipeappp.ui.dashboard.components.uploads

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.picnipeappp.R
import com.example.picnipeappp.ui.post.Post

class UploadViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val ivPostUpload = view.findViewById<ImageView>(R.id.ivPostUpload)
    val context = view.context
    fun render(postModel: Post, onClickListener:(Post) -> Unit){
        Glide.with(context).load(postModel.photo).into(ivPostUpload)
        ivPostUpload.setOnClickListener{
            onClickListener(postModel)
        }
    }
}