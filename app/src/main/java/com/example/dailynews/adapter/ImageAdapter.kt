package com.example.dailynews.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dailynews.R
import com.example.dailynews.data.TrendingData
import com.squareup.picasso.Picasso

class ImageAdapter(private val context: Context) : ListAdapter<TrendingData, ImageAdapter.ViewHolder>(
    DiffCallback()
) {

    class DiffCallback : DiffUtil.ItemCallback<TrendingData>() {
        override fun areItemsTheSame(oldItem: TrendingData, newItem: TrendingData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TrendingData, newItem: TrendingData): Boolean {
            return oldItem == newItem
        }
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val trendingImage = itemView.findViewById<ImageView>(R.id.trendingImageView)

        fun bindData(trend: TrendingData) {
            Picasso.get().load(trend.url).into(trendingImage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.main_card_layout,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trendImageItem = getItem(position)
        holder.bindData(trendImageItem)

        holder.itemView.setOnClickListener {
            val url = getItem(position).url
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            context.startActivity(intent)

        }
    }
}