package com.techstein.whatsappstatussaver.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.techstein.whatsappstatussaver.R

class ImageAdapter(private val context: Context, private val listOfImages: ArrayList<String>): RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageId = itemView.findViewById<ImageView>(R.id.statusImageId)
        val saveId = itemView.findViewById<ImageView>(R.id.statusSaveId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.status_item_view, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentImage = listOfImages[position]
        Glide.with(context).load("file://$currentImage").centerCrop().into(holder.imageId)
    }

    override fun getItemCount(): Int {
        return listOfImages.size
    }
}

interface Listen
{
    fun save(path: String)
}