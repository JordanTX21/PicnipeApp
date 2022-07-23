package com.example.picnipeappp.ui.dashboard.components.saveds

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.picnipeappp.R
import com.example.picnipeappp.ui.home.Post

class SavedAdapter(private val postList: List<Post>,private val onClickListener:(Post) -> Unit): RecyclerView.Adapter<SavedViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SavedViewHolder(layoutInflater.inflate(R.layout.item_saved, parent,false))
    }

    override fun onBindViewHolder(holder: SavedViewHolder, position: Int) {
        val item = postList[position]
        holder.render(item,onClickListener)
    }

    override fun getItemCount(): Int = postList.size
}