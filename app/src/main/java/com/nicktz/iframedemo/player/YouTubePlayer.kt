package com.nicktz.iframedemo.player

/**
 * Use this interface to control the playback of YouTube videos and to listen to their events.
 */
interface YouTubePlayer {

    fun cueVideo(videoId: String, startSeconds: Float)

    fun addListener(listener: YouTubePlayerListener): Boolean

    fun removeListener(listener: YouTubePlayerListener): Boolean

}
