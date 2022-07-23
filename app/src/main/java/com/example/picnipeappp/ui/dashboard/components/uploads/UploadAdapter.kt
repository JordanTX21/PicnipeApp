package com.example.picnipeappp.ui.dashboard.components.uploads

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.picnipeappp.R
import com.example.picnipeappp.ui.post.Post

class UploadAdapter(private val postList: List<Post>, private val onClickListener:(Post) -> Unit): RecyclerView.Adapter<UploadViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return UploadViewHolder(layoutInflater.inflate(R.layout.item_upload, parent,false))
    }

    override fun onBindViewHolder(holder: UploadViewHolder, position: Int) {
        val item = postList[position]
        holder.render(item,onClickListener)
    }

    override fun getItemCount(): Int = postList.size
}