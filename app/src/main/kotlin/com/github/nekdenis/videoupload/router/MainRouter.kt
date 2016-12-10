package com.github.nekdenis.videoupload.router

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore

class MainRouter {

    fun openCamera(requestCode: Int, isVideo: Boolean, activity: Activity): Boolean {

        val intent: Intent = Intent(if (isVideo) MediaStore.ACTION_VIDEO_CAPTURE else MediaStore.ACTION_IMAGE_CAPTURE)

        if (intent.resolveActivity(activity.packageManager) != null) {
            activity.startActivityForResult(intent, requestCode)
            return true
        } else {
            return false
        }
    }
}