package com.eco.musicplayer.audioplayer.music.layout.summary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eco.musicplayer.audioplayer.music.layout.summary.model.Category
import com.eco.musicplayer.databinding.ViewholderCategoryBinding

class CategoryAdapter(val items: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private lateinit var context: Context
    class CategoryViewHolder(val binding: ViewholderCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        context = parent.context
        val binding = ViewholderCategoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = items[position]
        holder.binding.txtTile.text = item.title

    }

    override fun getItemCount(): Int = items.size
}