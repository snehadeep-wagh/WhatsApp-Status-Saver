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
import kotlinx.android.synthetic.main.download_item_view.view.*
import kotlinx.android.synthetic.main.status_item_view.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DownloadVideoAdapter(private val context: Context, private val oldList: ArrayList<Model>, private val itemListner: DownloadVideoListener) : RecyclerView.Adapter<DownloadVideoAdapter.DownloadVideoVideoHolder>() {
    inner class DownloadVideoVideoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail: ImageView = itemView.downloadImageId
        val downloadButton: ImageView = itemView.downloadShareId
        val timeId: TextView = itemView.downloadvideoTimeId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadVideoVideoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.download_item_view, parent, false)
        return DownloadVideoVideoHolder(view)
    }

    override fun onBindViewHolder(holder: DownloadVideoVideoHolder, position: Int) {
        val currentImage = oldList[position]
        GlobalScope.launch {
            uploadThumb(context, holder, currentImage)
        }

        holder.downloadButton.setOnClickListener {
            itemListner.onClickShareButton(currentImage.path)
        }

        holder.thumbnail.setOnClickListener {
            itemListner.onClickVideo(currentImage)
        }

    }

    override fun getItemCount(): Int {
        return oldList.size
    }
}

private suspend fun uploadThumb(context: Context, holder: DownloadVideoAdapter.DownloadVideoVideoHolder, currentImage: Model)
{
    withContext(Dispatchers.IO)
    {
        val thumb = Glide.with(context)
                .load("file://${currentImage.path}")
                .centerCrop()

        withContext(Dispatchers.Main)
        {
            thumb.into(holder.thumbnail)
            holder.timeId.text = currentImage.dura
        }
    }
}


interface DownloadVideoListener {
    fun onClickShareButton(path: String)
    fun onClickVideo(currentImage: Model)
}