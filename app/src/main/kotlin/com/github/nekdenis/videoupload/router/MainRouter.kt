package com.github.nekdenis.videoupload.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import com.github.nekdenis.videoupload.EditorActivity

class MainRouter {

    fun openCamera(requestCode: Int, isVideo: Boolean, activity: Activity): Boolean {

        val intent = Intent(if (isVideo) MediaStore.ACTION_VIDEO_CAPTURE else MediaStore.ACTION_IMAGE_CAPTURE)

        if (intent.resolveActivity(activity.packageManager) != null) {
            activity.startActivityForResult(intent, requestCode)
            return true
        } else {
            return false
        }
    }

    fun startEditor(context: Context, videoPath: String) {
        val intent = Intent(context, EditorActivity::class.java)
        intent.putExtra(RouterConstants.EXTRA_VIDEO_PATH, videoPath)
        context.startActivity(intent)
    }
}