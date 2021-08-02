package com.techstein.whatsappstatussaver.fragments

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.core.net.toUri
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.techstein.whatsappstatussaver.Model
import com.techstein.whatsappstatussaver.R
import com.techstein.whatsappstatussaver.adapters.*
import com.techstein.whatsappstatussaver.constants.MyConstants
import com.techstein.whatsappstatussaver.mediaplayeractivities.VideoPlayerActivity
import kotlinx.android.synthetic.main.fragment_downloads.view.*
import java.io.File

class DownloadsFragment : Fragment() {

    var imageButton: Button? = null
    var videoButton: Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_downloads, container, false)
        val MyAdapter = view.DownloadAdapterId
        imageButton = view.findViewById(R.id.Download_image_buttonId)
        videoButton = view.findViewById(R.id.Download_video_buttonId)
        var listOfImages = getDownloadImages()
        var listOfVideos = getDownloadVideo()

        //Image Listener -----
        val downloadImageListen = object : DownloadImageListener
        {
            override fun onClickShareButton(file: String) {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    this.type = "image/*"
                    this.putExtra(Intent.EXTRA_STREAM, file.toUri())
                    startActivity(this)
                }
            }

            override fun onClickImage(currentImage: String) {
                showImage(currentImage)
            }
        }

        // Video Listener -----
        val downloadVideoListen = object : DownloadVideoListener
        {
            override fun onClickShareButton(path: String) {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    this.type = "video/*"
                    this.putExtra(Intent.EXTRA_STREAM, path.toUri())
                    startActivity(this)
                }
            }

            override fun onClickVideo(currentVideo: Model) {
                Intent(activity, VideoPlayerActivity::class.java).apply {
                    this.putExtra("url", currentVideo.path)
                    startActivity(this)
                }
            }

        }


        //Initialize Adapter =
        MyAdapter.layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        MyAdapter.adapter = DownloadImageAdapter(context!!, listOfImages, downloadImageListen)

        imageButton!!.setOnClickListener {
            listOfImages = getDownloadImages()
            MyAdapter.adapter = DownloadImageAdapter(context!!, listOfImages, downloadImageListen)
            MyAdapter.adapter!!.notifyDataSetChanged()
        }

        videoButton!!.setOnClickListener {
            listOfVideos = getDownloadVideo()
            MyAdapter.adapter = DownloadVideoAdapter(context!!, listOfVideos, downloadVideoListen)
            MyAdapter.adapter!!.notifyDataSetChanged()
        }
//        // Initialize Image Adapter ----
//        imageAdapter.layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
//        imageAdapter.adapter = DownloadImageAdapter(context!!, listOfImages, downloadImageListen)
//
//        // Initialize Video Adapter ----
//        videoAdapter.layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
//        videoAdapter.adapter = DownloadVideoAdapter(context!!, listOfVideos, downloadVideoListen)
//
//        //On Refreshing ----
//        view.downloadRefreshId.setOnRefreshListener {
//            listOfImages = getDownloadImages()
//            imageAdapter.adapter = DownloadImageAdapter(context!!, listOfImages, downloadImageListen)
//            imageAdapter.adapter!!.notifyDataSetChanged()
//
//            listOfVideos = getDownloadVideo()
//            videoAdapter.adapter = DownloadVideoAdapter(context!!, listOfVideos, downloadVideoListen)
//            videoAdapter.adapter!!.notifyDataSetChanged()
//            view.downloadRefreshId.isRefreshing = false
//        }
        return view
    }

    fun getDownloadImages(): ArrayList<String>
    {
        val ImageList = ArrayList<String>()
        val file: File = MyConstants.IMAGE_FILE_LOC
        if(file.exists())
        {
            val listOfImages = file.listFiles()
            if(listOfImages!= null && listOfImages.isNotEmpty())
            {
                for(image in listOfImages)
                {
                    ImageList.add(image.absolutePath)
                }
            }
        }

        return ImageList
    }

    private fun getDownloadVideo(): ArrayList<Model>
    {
        val videoList = ArrayList<Model>()
        val file = MyConstants.VIDEO_FILE_LOC
        if(file.exists())
        {
            val listOfVideos = file.listFiles()
            if(listOfVideos != null && listOfVideos.isNotEmpty())
            {
                for(video in listOfVideos)
                {
                    val timeUri = MediaPlayer.create(context, video.absolutePath.toUri())
                    val time = "00:" + (timeUri.duration/1000).toString()
                    videoList.add(Model(video.absolutePath, time))
                }
            }
        }
        return videoList
    }

    private fun showImage(currentImage: String)
    {
        val dialog: Dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val imageView = ImageView(activity)
        Glide.with(context!!).load("file://${currentImage}").into(imageView)
        dialog.addContentView(imageView, LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ))
        dialog.setCancelable(true)
        dialog.show()
    }

}