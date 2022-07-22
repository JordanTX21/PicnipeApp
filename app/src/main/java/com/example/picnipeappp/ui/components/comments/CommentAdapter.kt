package com.example.picnipeappp.ui.components.comments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.picnipeappp.R

class CommentAdapter(private val commentsList: List<Comment>)  : RecyclerView.Adapter<CommentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CommentViewHolder(layoutInflater.inflate(R.layout.item_comment, parent,false))
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val item = commentsList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = commentsList.size
}