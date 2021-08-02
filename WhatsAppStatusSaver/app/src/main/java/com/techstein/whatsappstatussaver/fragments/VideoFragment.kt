package com.techstein.whatsappstatussaver.fragments

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.GridLayoutManager
import com.techstein.whatsappstatussaver.R
import com.techstein.whatsappstatussaver.adapters.VideoAdapter
import com.techstein.whatsappstatussaver.constants.MyConstants
import com.techstein.whatsappstatussaver.Model
import com.techstein.whatsappstatussaver.adapters.VideoListen
import com.techstein.whatsappstatussaver.mediaplayeractivities.VideoPlayerActivity
import kotlinx.android.synthetic.main.fragment_image.view.*
import kotlinx.android.synthetic.main.fragment_video.view.*
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class VideoFragment : Fragment() {
    lateinit var videoList: ArrayList<Model>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_video, container, false)

        val videoListen = object : VideoListen
        {
            override fun onClickSaveButton(path: String) {
                copyVideo(path)
                Toast.makeText(activity, "Saved button", Toast.LENGTH_SHORT).show()
            }

            override fun onClickVideo(currentVideo: Model) {
                Intent(activity, VideoPlayerActivity::class.java).apply {
                    this.putExtra("url", currentVideo.path)
                    startActivity(this)
                }
            }
        }

        videoList = getVideoData()
        view.VideoAdapterId.layoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
        view.VideoAdapterId.adapter = VideoAdapter(context!!, videoList, videoListen)

        view.VideoRefreshLayoutId.setOnRefreshListener {
            videoList = getVideoData()
            view.VideoAdapterId.adapter = VideoAdapter(context!!, videoList, videoListen)
            view.VideoAdapterId.adapter!!.notifyDataSetChanged()
            view.VideoRefreshLayoutId.isRefreshing = false
        }
        return view
    }

    private fun getVideoData(): ArrayList<Model> {
        val listOfVideos = ArrayList<Model>()

        val path = MyConstants.STATUS_LOC
        val lastFile = path.listFiles()

        //Check size ---
        lastFile?.let {
            Log.i("loc size", it.size.toString())
        }

        if(lastFile != null && lastFile.isNotEmpty())
        {
            for(video in lastFile)
            {
                if(video.name.endsWith(".mp4") || video.name.endsWith(".gif") || video.name.endsWith("avi") || video.name.endsWith(".mkv"))
                {
                    val mpl: MediaPlayer = MediaPlayer.create(context!!, video.absolutePath.toUri())
                    val videoDuration: String = "00:" + (mpl.duration/1000).toString()

                    val model = Model(video.absolutePath, videoDuration)
                    listOfVideos.add(model)
                }
            }
        }
        else
        {
            Toast.makeText(activity, "No status found!", Toast.LENGTH_SHORT).show()
        }


        return listOfVideos
    }

    private fun createFolder(): File
    {
        var path: String? = null
        val imageFolder = MyConstants.VIDEO_FILE_LOC
        if(!imageFolder.exists())
        {
            imageFolder.mkdirs()
            Log.i("check", "created")
        }

        return imageFolder
    }

    private fun copyVideo(sourcePath: String) {
        createFolder()?.let { folder ->
            val file = File(sourcePath)
            val destination = File(folder.absolutePath + File.separator + file.name)
            try {
                file.copyTo(destination, false, DEFAULT_BUFFER_SIZE)
            } catch (e: Exception) {
                Toast.makeText(activity, "Already downloaded", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

//"file://${currentImage.path}"