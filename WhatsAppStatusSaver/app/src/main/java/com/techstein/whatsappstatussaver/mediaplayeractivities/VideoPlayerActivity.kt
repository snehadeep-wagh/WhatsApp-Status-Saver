package com.techstein.whatsappstatussaver.mediaplayeractivities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import com.techstein.whatsappstatussaver.R
import kotlinx.android.synthetic.main.activity_video_player.*

class VideoPlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        val videoPath = intent.getStringExtra("url")
        val uri = Uri.parse(videoPath)
        videoPlayerId.setMediaController(MediaController(this))
        videoPlayerId.setVideoURI(uri)
        videoPlayerId.requestFocus()
        videoPlayerId.start()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}