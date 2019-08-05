package com.nicktz.iframedemo.player

import org.json.JSONException
import org.json.JSONObject

/**
 * Options used to configure the IFrame Player. All the options are listed here:
 * [IFrame player parameters](https://developers.google.com/youtube/player_parameters#Parameters)
 */
class IFramePlayerOptions private constructor(private val playerOptions: JSONObject) {

    companion object {
        val default = IFramePlayerOptions.Builder().build()
    }

    override fun toString(): String {
        return playerOptions.toString()
    }

    class Builder {
        companion object {
            private const val AUTO_PLAY = "autoplay"
            private const val CONTROLS = "controls"
            private const val ENABLE_JS_API = "enablejsapi"
            private const val FS = "fs"
            private const val ORIGIN = "origin"
            private const val REL = "rel"
            private const val SHOW_INFO = "showinfo"
            private const val IV_LOAD_POLICY = "iv_load_policy"
            private const val MODEST_BRANDING = "modestbranding"
            private const val CC_LOAD_POLICY = "cc_load_policy"
            private const val DISABLE_KB = "disablekb"
            private const val AUTO_HIDE = "autohide"
            private const val PLAY_INLINE = "playinline"

        }

        private val builderOptions = JSONObject()

        init {
            addInt(AUTO_PLAY, 0)
            addInt(CONTROLS, 0)
            addInt(ENABLE_JS_API, 1)
            addInt(FS, 0)
            addString(ORIGIN, "https://www.youtube.com")
            addInt(REL, 0)
            addInt(SHOW_INFO, 0)
            addInt(IV_LOAD_POLICY, 3)
            addInt(MODEST_BRANDING, 1)
            addInt(CC_LOAD_POLICY, 0)
            addInt(DISABLE_KB, 0)
            addInt(AUTO_HIDE, 0)
            addInt(PLAY_INLINE, 1)
        }

        fun build(): IFramePlayerOptions {
            return IFramePlayerOptions(builderOptions)
        }

        private fun addString(key: String, value: String) {
            try {
                builderOptions.put(key, value)
            } catch (e: JSONException) {
                throw RuntimeException("Illegal JSON value $key: $value")
            }

        }

        private fun addInt(key: String, value: Int) {
            try {
                builderOptions.put(key, value)
            } catch (e: JSONException) {
                throw RuntimeException("Illegal JSON value $key: $value")
            }

        }
    }
}
