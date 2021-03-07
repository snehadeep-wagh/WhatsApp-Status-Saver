package com.techstein.whatsappstatussaver.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.techstein.whatsappstatussaver.Model
import com.techstein.whatsappstatussaver.R
import kotlinx.android.synthetic.main.status_item_view.view.*

class VideoAdapter(private val context: Context, private val oldList: ArrayList<Model>): RecyclerView.Adapter<VideoAdapter.StatusViewHolder>() {

    inner class StatusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail: ImageView = itemView.statusImageId
        val downloadButton: ImageView = itemView.statusSaveId
        val timeId: TextView = itemView.videoTimeId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.status_item_view, parent, false)
        return StatusViewHolder(view)
    }

    override fun onBindViewHolder(holder: StatusViewHolder, position: Int) {
        val currentImage = oldList[position]
        Glide.with(context)
                .load("file://${currentImage.path}")
                .centerCrop()
                .into(holder.thumbnail)

        holder.timeId.text = currentImage.dura
    }

    override fun getItemCount(): Int {
        return oldList.size
    }
}