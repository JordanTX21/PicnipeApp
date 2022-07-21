package com.example.picnipeappp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.picnipeappp.R
import com.example.picnipeappp.ui.home.Post

class PostAdapter(private val postList: List<Post>,private val onClickListener:(Post) -> Unit) : RecyclerView.Adapter<PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent,false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item =  postList[position]
        holder.render(item,onClickListener)
    }

    override fun getItemCount(): Int = postList.size
}