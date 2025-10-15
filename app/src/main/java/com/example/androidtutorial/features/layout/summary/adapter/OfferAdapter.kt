package com.example.androidtutorial.features.layout.summary.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidtutorial.databinding.ViewholderOfferBinding
import com.example.androidtutorial.features.layout.summary.model.Item

class OfferAdapter(val items: List<Item>) : RecyclerView.Adapter<OfferAdapter.OfferViewHolder>() {
    private var context: Context? = null

    class OfferViewHolder(val binding: ViewholderOfferBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        context = parent.context
        val binding = ViewholderOfferBinding.inflate(LayoutInflater.from(context), parent, false)
        return OfferViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        with(holder) {
            binding.txtTitle.text = items[position].title
            binding.txtPrice.text = "$" + items[position].price.toString()

            Glide.with(itemView.context)
                .load(items[position].picUrl)
                .into(holder.binding.pic)
        }
    }

    override fun getItemCount(): Int = items.size
}