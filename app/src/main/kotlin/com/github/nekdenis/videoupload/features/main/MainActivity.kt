package com.github.nekdenis.videoupload.features.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.github.nekdenis.videoupload.R
import com.github.nekdenis.videoupload.features.RxBaseActivity
import com.github.nekdenis.videoupload.router.MainRouter
import com.github.nekdenis.videoupload.router.RouterConstants.REQUEST_CODE_VIDEO
import com.github.nekdenis.videoupload.util.DLog
import com.github.nekdenis.videoupload.util.getRealPathFromURI
import kotlinx.android.synthetic.main.activity_main.*

const val MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 1

class MainActivity : RxBaseActivity() {

    val TAG = "MainActivity"

    val router: MainRouter = MainRouter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addButton.setOnClickListener {
            if (checkPermissions()) {
                openCamera()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_VIDEO && resultCode == RESULT_OK) {
            loadCapturedVideo(data)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>?, grantResults: IntArray?) {
        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_STORAGE && grantResults!!.isNotEmpty()) {
            openCamera()
        }
    }

    private fun checkPermissions(): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        MY_PERMISSIONS_REQUEST_WRITE_STORAGE)
                return false
            }
        }
        return true
    }


    private fun openCamera() {
        router.openCamera(REQUEST_CODE_VIDEO, true, this)
    }

    private fun loadCapturedVideo(data: Intent?) {
        if (data != null) {
            val videoUri = data.data
            val videoPath = videoUri.getRealPathFromURI(this)
            if (videoPath != null) {
                DLog.d(TAG, "video path: $videoPath")

                proceedButton.visibility = View.VISIBLE
                proceedButton.setOnClickListener {
                    router.startEditor(this, videoPath)
                }
                addButton.visibility = View.GONE

                videoView.setVideoURI(videoUri)
                videoView.start()
            } else {
                Toast.makeText(this, R.string.action_add_error, Toast.LENGTH_LONG).show()
            }
        }
    }

}

