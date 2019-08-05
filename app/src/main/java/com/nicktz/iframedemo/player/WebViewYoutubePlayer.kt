package com.nicktz.iframedemo.player

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.webkit.WebSettings
import android.webkit.WebView
import com.nicktz.iframedemo.R
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

class WebViewYouTubePlayer constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : WebView(context, attrs, defStyleAttr), YouTubePlayer, YouTubePlayerBridge.YouTubePlayerBridgeCallbacks {

    private lateinit var youTubePlayerInitListener: (YouTubePlayer) -> Unit

    private val youTubePlayerListeners = HashSet<YouTubePlayerListener>()

    private val mainThreadHandler: Handler = Handler(Looper.getMainLooper())

    internal fun initialize(initListener: (YouTubePlayer) -> Unit) {
        youTubePlayerInitListener = initListener
        initWebView(IFramePlayerOptions.default)
    }

    override fun cueVideo(videoId: String, startSeconds: Float) {
        mainThreadHandler.post { loadUrl("javascript:cueVideo('$videoId', $startSeconds)") }
    }

    override fun destroy() {
        mainThreadHandler.removeCallbacksAndMessages(null)
        super.destroy()
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(playerOptions: IFramePlayerOptions) {
        settings.javaScriptEnabled = true
        settings.mediaPlaybackRequiresUserGesture = false
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        addJavascriptInterface(YouTubePlayerBridge(this), "YouTubePlayerBridge")
        val htmlPage = readHTMLFromUTF8File(resources.openRawResource(R.raw.youtube_player))
            .replace("<<injectedPlayerVars>>", playerOptions.toString())
        loadDataWithBaseURL("https://www.youtube.com", htmlPage, "text/html", "utf-8", null)
    }

    private fun readHTMLFromUTF8File(inputStream: InputStream): String {
        try {
            val bufferedReader = BufferedReader(InputStreamReader(inputStream, "utf-8"))

            var currentLine: String? = bufferedReader.readLine()
            val sb = StringBuilder()

            while (currentLine != null) {
                sb.append(currentLine).append("\n")
                currentLine = bufferedReader.readLine()
            }

            return sb.toString()
        } catch (e: Exception) {
            throw RuntimeException("Can't parse HTML file.")
        } finally {
            inputStream.close()
        }
    }

    override fun getListeners(): Collection<YouTubePlayerListener> {
        return Collections.unmodifiableCollection(HashSet(youTubePlayerListeners))
    }

    override fun addListener(listener: YouTubePlayerListener): Boolean {
        return youTubePlayerListeners.add(listener)
    }

    override fun removeListener(listener: YouTubePlayerListener): Boolean {
        return youTubePlayerListeners.remove(listener)
    }

    override fun getInstance(): YouTubePlayer = this

    override fun onYouTubeIFrameAPIReady() = youTubePlayerInitListener(this)


}
