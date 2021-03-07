package com.techstein.whatsappstatussaver.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.techstein.whatsappstatussaver.R
import com.techstein.whatsappstatussaver.adapters.VideoAdapter
import com.techstein.whatsappstatussaver.constants.MyConstants
import com.techstein.whatsappstatussaver.Model
import kotlinx.android.synthetic.main.fragment_video.view.*

class VideoFragment : Fragment() {
    lateinit var videoList: ArrayList<Model>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_video, container, false)
        videoList = getVideoData()
        view.VideoAdapterId.layoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
        view.VideoAdapterId.adapter = VideoAdapter(context!!, videoList)

        return view
    }

//    override fun onStart() {
//        super.onStart()
//        view!!.progressBar.isVisible = true
//    }

    fun getVideoData(): ArrayList<Model> {
        val listOfVideos = ArrayList<Model>()

        val path = MyConstants.STATUS_LOC
        val lastFile = path.listFiles()

        //Check size ---
        lastFile?.let {
            Log.i("loc size", it.size.toString())
        }

        if(lastFile != null && lastFile.isNotEmpty())
        {
            Log.i("loc", "Entered loop")
            for(video in lastFile)
            {
                if(video.name.endsWith(".mp4") || video.name.endsWith(".gif") || video.name.endsWith("avi") || video.name.endsWith(".mkv"))
                {

                    val mpl: MediaPlayer = MediaPlayer.create(context!!, video.absolutePath.toUri())
                    val videoDuration: String = "00:" + (mpl.duration/1000).toString()

                    val model = Model(video.absolutePath, videoDuration)
                    listOfVideos.add(model)


                    Log.i("data", video.name)
                }
                else
                {
                    Log.i("data", video.name)
                }
            }
        }
        else
        {
            Toast.makeText(activity, "No status found!", Toast.LENGTH_SHORT).show()
        }

        return listOfVideos
    }

}