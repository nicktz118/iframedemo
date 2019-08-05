package com.nicktz.iframedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nicktz.iframedemo.player.YouTubePlayer
import com.nicktz.iframedemo.player.YouTubePlayerListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        youtubePlayerView.initialize(object: YouTubePlayerListener {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo("htNtpBblO_s", 0f)
            }
        })
    }
}
