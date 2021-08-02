package com.techstein.whatsappstatussaver.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.techstein.whatsappstatussaver.R


class DownloadImageAdapter(private val context: Context, private val listOfImages: ArrayList<String>, private val imageLister: DownloadImageListener): RecyclerView.Adapter<DownloadImageAdapter.DownloadImageViewHolder>() {

    inner class DownloadImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageId = itemView.findViewById<ImageView>(R.id.downloadImageId)
        val shareId = itemView.findViewById<ImageView>(R.id.downloadShareId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.download_item_view, parent, false)
        return DownloadImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: DownloadImageViewHolder, position: Int) {
        val currentImage = listOfImages[position]
        Glide.with(context).load("file://$currentImage").centerCrop().into(holder.imageId)
        Log.i("currentImage", currentImage)

        holder.imageId.setOnClickListener {
            imageLister.onClickImage(currentImage)
        }

        holder.shareId.setOnClickListener {
            imageLister.onClickShareButton(currentImage)
        }
    }

    override fun getItemCount(): Int {
        return listOfImages.size
    }

}

interface DownloadImageListener
{
    fun onClickShareButton(file: String)
    fun onClickImage(currentImage: String)
}