package com.example.picnipeappp.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.picnipeappp.R

class PostAdapter(private val context : Context , private var itemsList: List<PostItem>) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.each_row, parent,false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val postList =  itemsList[position]

        holder.ivPost.setImageResource(postList.imagen)

    }


    override fun getItemCount(): Int {
        return itemsList.size
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPost = itemView.findViewById<ImageView>(R.id.ivPost)
    }
}