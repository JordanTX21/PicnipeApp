package com.example.picnipeappp.ui.dashboard.components.saveds

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.picnipeappp.R
import com.example.picnipeappp.ui.post.Post

class SavedViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val ivPostSaved = view.findViewById<ImageView>(R.id.ivPostSaved)
    val context = view.context
    fun render(postModel: Post, onClickListener:(Post)->Unit){
        Glide.with(context).load(postModel.photo).into(ivPostSaved)
        ivPostSaved.setOnClickListener{
            onClickListener(postModel)
        }
    }
}