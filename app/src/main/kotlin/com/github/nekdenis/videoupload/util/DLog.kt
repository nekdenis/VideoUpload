@file:JvmName("ExtensionsUtils")

package com.github.nekdenis.videoupload.util

import android.util.Log
import com.github.nekdenis.videoupload.BuildConfig

object DLog {

    fun e(tag: String, e: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, e.message ?: "")
        }
    }

    fun e(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message)
        }
    }

    fun d(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message)
        }
    }

}
