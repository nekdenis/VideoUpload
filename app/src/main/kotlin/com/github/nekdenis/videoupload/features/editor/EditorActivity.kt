package com.github.nekdenis.videoupload

import android.os.Bundle
import android.widget.Toast
import com.github.nekdenis.videoupload.features.RxBaseActivity
import com.github.nekdenis.videoupload.features.UploadModel
import com.github.nekdenis.videoupload.router.RouterConstants
import com.github.nekdenis.videoupload.util.DLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_editor.*
import javax.inject.Inject

class EditorActivity : RxBaseActivity() {

    val TAG = "EditorActivity"

    @Inject lateinit var uploadModel: UploadModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        VUApp.uploadComponent.inject(this)
        setContentView(R.layout.activity_editor)
        loadCapturedVideo(intent.getStringExtra(RouterConstants.EXTRA_VIDEO_PATH))
    }

    override fun onDestroy() {
        timelineView.destroy()
        super.onDestroy()
    }

    private fun loadCapturedVideo(videoPath: String) {
        DLog.d(TAG, "video path: $videoPath")
        timelineView.setVideoPath(videoPath)
        videoView.setVideoPath(videoPath)
        videoView.start()
        uploadVideo(videoPath)
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
