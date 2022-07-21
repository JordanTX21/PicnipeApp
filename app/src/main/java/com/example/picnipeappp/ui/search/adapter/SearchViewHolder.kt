package com.example.picnipeappp.ui.search.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.picnipeappp.databinding.ItemSearchBinding
import com.example.picnipeappp.ui.search.DinamicSearch

class SearchViewHolder(view:View): RecyclerView.ViewHolder(view) {
    val binding = ItemSearchBinding.bind(view)

    fun render(searchModel: DinamicSearch){
        Glide.with(binding.ivImgSearch.context).load(searchModel.photo)
            .into(binding.ivImgSearch)

        binding.titleSearch.text = searchModel.title
        binding.detailSearch.text = searchModel.detail

    }
}