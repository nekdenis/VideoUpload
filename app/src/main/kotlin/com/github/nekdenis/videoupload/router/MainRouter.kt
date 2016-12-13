package com.github.nekdenis.videoupload.router

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import com.github.nekdenis.videoupload.R
import com.github.nekdenis.videoupload.features.editor.ui.EditorActivity


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

    fun openBrowser(context: Context, trimmedVideoUrl: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(trimmedVideoUrl)
        context.startActivity(i)
    }

    fun downloadFile(context: Context, url: String, videoName: String): Long {

        val downloadReference: Long

        val downloadManager: DownloadManager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(Uri.parse(url))

        request.setTitle(context.getString(R.string.download_video_title))
        request.setDescription(context.getString(R.string.download_video_description))

        request.setDestinationInExternalFilesDir(context,
                Environment.DIRECTORY_DOWNLOADS, videoName)
        downloadReference = downloadManager.enqueue(request)
        return downloadReference
    }
}