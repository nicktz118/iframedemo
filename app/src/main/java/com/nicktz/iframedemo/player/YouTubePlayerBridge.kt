package com.nicktz.iframedemo.player

import android.os.Handler
import android.os.Looper
import androidx.annotation.RestrictTo

import android.text.TextUtils
import android.webkit.JavascriptInterface


/**
 * Bridge used for Javascript-Java communication.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
class YouTubePlayerBridge(private val youTubePlayerOwner: YouTubePlayerBridgeCallbacks) {

    private val mainThreadHandler: Handler = Handler(Looper.getMainLooper())

    interface YouTubePlayerBridgeCallbacks {
        fun getInstance(): YouTubePlayer
        fun getListeners(): Collection<YouTubePlayerListener>
        fun onYouTubeIFrameAPIReady()
    }

    @JavascriptInterface
    fun sendYouTubeIFrameAPIReady() =
        mainThreadHandler.post { youTubePlayerOwner.onYouTubeIFrameAPIReady() }

    @JavascriptInterface
    fun sendReady() {
        mainThreadHandler.post {
            for (listener in youTubePlayerOwner.getListeners())
                listener.onReady(youTubePlayerOwner.getInstance())
        }
    }
}
