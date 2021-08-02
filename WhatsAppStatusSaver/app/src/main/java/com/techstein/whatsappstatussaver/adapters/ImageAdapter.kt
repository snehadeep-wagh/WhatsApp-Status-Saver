package com.techstein.whatsappstatussaver.adapters

import android.content.Context
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.techstein.whatsappstatussaver.Model
import com.techstein.whatsappstatussaver.R
import com.techstein.whatsappstatussaver.constants.MyConstants
import java.io.File

class ImageAdapter(private val context: Context, private val listOfImages: ArrayList<String>, private val imageLister: ImageListen): RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

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
        Log.i("currentImage", currentImage)

        holder.imageId.setOnClickListener {
            imageLister.onClickImage(currentImage)
        }

        holder.saveId.setOnClickListener {
            imageLister.onClickSaveButton(currentImage)
        }
    }

    override fun getItemCount(): Int {
        return listOfImages.size
    }
}

interface ImageListen
{
    fun onClickSaveButton(file: String)
    fun onClickImage(currentImage: String)
}