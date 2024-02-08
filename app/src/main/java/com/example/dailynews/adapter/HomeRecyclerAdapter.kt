package com.example.dailynews.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.dailynews.R
import com.example.dailynews.data.HomeRecyclerData
import com.squareup.picasso.Picasso

class HomeRecyclerAdapter(private val context : Context , private var items: ArrayList<HomeRecyclerData>) :
    RecyclerView.Adapter<HomeRecyclerAdapter.RecyclerViewHolder>() {
    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var layout: ConstraintLayout
        var title: TextView? = null
        var time: TextView? = null
        var reads: TextView? = null
        var image: ImageView? = null

        init {
            this.layout = itemView.findViewById(R.id.rv_home_constraint_layout)
            this.title = itemView.findViewById(R.id.title_rv_home)
            this.time = itemView.findViewById(R.id.time_rv_home)
            this.reads = itemView.findViewById(R.id.reads_rv_home)
            this.image = itemView.findViewById(R.id.image_rv_home)
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeRecyclerAdapter.RecyclerViewHolder {
        val itemView =
            LayoutInflater.from(parent?.context).inflate(R.layout.home_rv_layout, parent, false)
        return RecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HomeRecyclerAdapter.RecyclerViewHolder, position: Int) {
        val currItem = items[position]
        holder.title?.text = currItem.title
        holder.time?.text = currItem.time
        holder.reads?.text = currItem.read
        Picasso.get().load(currItem.imageUrl).resize(110,110).centerCrop().into(holder.image)

        holder.layout.setOnClickListener {
            Toast.makeText(context , holder.title?.text , Toast.LENGTH_LONG).show()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}