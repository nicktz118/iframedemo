package com.nicktz.iframedemo.player

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout

internal class YouTubePlayerView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    FrameLayout(context, attrs, defStyleAttr){
    constructor(context: Context): this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet? = null): this(context, attrs, 0)

    private val youTubePlayer: WebViewYouTubePlayer = WebViewYouTubePlayer(context)

    init {
        addView(youTubePlayer, LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
    }

    fun initialize(youTubePlayerListener: YouTubePlayerListener) {
        youTubePlayer.initialize {it.addListener(youTubePlayerListener)}
    }
}