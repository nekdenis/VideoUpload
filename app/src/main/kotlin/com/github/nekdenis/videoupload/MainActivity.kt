package com.github.nekdenis.videoupload

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.github.nekdenis.videoupload.features.RxBaseActivity
import com.github.nekdenis.videoupload.features.UploadModel
import com.github.nekdenis.videoupload.router.MainRouter
import com.github.nekdenis.videoupload.router.RouterConstants.REQUEST_CODE_VIDEO
import com.github.nekdenis.videoupload.util.DLog
import com.github.nekdenis.videoupload.util.getRealPathFromURI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : RxBaseActivity() {

    val TAG = "MainActivity"

    val router: MainRouter = MainRouter()

    @Inject lateinit var uploadModel: UploadModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        VUApp.uploadComponent.inject(this)
        setContentView(R.layout.activity_main)
        addButton.setOnClickListener {
            openCamera()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_VIDEO && resultCode == RESULT_OK) {
            loadCapturedVideo(data)
        }
    }

    override fun onDestroy() {
        timelineView.destroy()
        super.onDestroy()
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
                timelineView.setVideoPath(videoPath)
                videoView.setVideoURI(videoUri)
                videoView.start()
                uploadVideo(videoPath)
            } else {
                Toast.makeText(this, R.string.action_add_error, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun uploadVideo(pathToVideo: String) {
        val subscription = uploadModel.uploadVideo(pathToVideo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            Toast.makeText(this, "video uploaded", Toast.LENGTH_LONG).show()
                            DLog.d(TAG, "result: ${result.responseValues}")
                            val trimVideoUrl = uploadModel.getTrimVideoUrl(result.name)
                            DLog.d(TAG, "trimmed: $trimVideoUrl")
                        },
                        { e ->
                            DLog.e(TAG, "error: ${e.message}")
                        }
                )
        disposables.add(subscription)
    }
}
