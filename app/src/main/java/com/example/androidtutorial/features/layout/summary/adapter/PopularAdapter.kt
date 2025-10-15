package com.example.androidtutorial.features.layout.summary.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidtutorial.databinding.ViewholderPopularBinding
import com.example.androidtutorial.features.layout.summary.model.Item

class PopularAdapter(val items: List<Item>) :
    RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {
    private var context: Context? = null

    class PopularViewHolder(val binding: ViewholderPopularBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        context = parent.context
        val binding = ViewholderPopularBinding.inflate(LayoutInflater.from(context), parent, false)
        return PopularViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        with(holder) {
            binding.txtTitle.text = items[position].title
            binding.txtPrice.text = "$" + items[position].price.toString()
            binding.ratingBar.rating = items[position].rating.toFloat()

            Glide.with(itemView.context)
                .load(items[position].picUrl)
                .into(holder.binding.pic)
        }
    }

    override fun getItemCount(): Int = items.size
}