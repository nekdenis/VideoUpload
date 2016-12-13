package com.github.nekdenis.videoupload.features.editor.ui

import android.os.Bundle
import android.view.View
import com.github.nekdenis.videoupload.R
import com.github.nekdenis.videoupload.VUApp
import com.github.nekdenis.videoupload.features.RxBaseActivity
import com.github.nekdenis.videoupload.features.editor.EditorContract
import com.github.nekdenis.videoupload.features.editor.data.TrimPartInfo
import com.github.nekdenis.videoupload.features.editor.logic.EditorPresenter
import com.github.nekdenis.videoupload.router.MainRouter
import com.github.nekdenis.videoupload.router.RouterConstants
import kotlinx.android.synthetic.main.activity_editor.*

class EditorActivity : RxBaseActivity(), EditorContract.View, TrimView.TrimViewCallback {

    val TAG = "EditorActivity"

    private val presenter = EditorPresenter()
    private val trimViews = mutableMapOf<TrimPartInfo, TrimView>()
    private val router = MainRouter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        VUApp.uploadComponent.inject(this)
        setContentView(R.layout.activity_editor)
        presenter.attachView(this)
        presenter.setVideo(intent.getStringExtra(RouterConstants.EXTRA_VIDEO_PATH))
        proceedButton.setOnClickListener { presenter.onProceed(trimViews.keys.toList()) }
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    //region EditorContract.View

    override fun showVideo(videoPath: String) {
        videoView.setVideoPath(videoPath)
        videoView.start()
    }

    override fun addVideoTrim(videoPath: String, trimPartInfo: TrimPartInfo) {
        val trimView = TrimView(this)
        trimViews.put(trimPartInfo, trimView)
        trimView.initVideo(videoPath, trimPartInfo)
        trimView.trimViewCallback = this
        trimView.setAsCurrent()
        timelineContainer.addView(trimView)
    }

    override fun removeVideoTrim(trimPartInfo: TrimPartInfo) {
        val trimView = trimViews[trimPartInfo]
        if (trimView != null) {
            trimViews.remove(trimPartInfo)
            timelineContainer.removeView(trimView)
        }
    }

    override fun setAsAppliedVideoTrim(trimPartInfo: TrimPartInfo) {
        val trimView = trimViews[trimPartInfo]
        trimView!!.setAsApplied()
    }

    override fun showProgress(isVisible: Boolean) {
        if (isVisible) {
            progress.visibility = View.VISIBLE
        } else {
            progress.visibility = View.GONE
        }
    }

    override fun showProceedButton(isVisible: Boolean) {
        if (isVisible) {
            proceedButton.visibility = View.VISIBLE
        } else {
            proceedButton.visibility = View.GONE
        }
    }

    override fun openVideoInBrowser(trimmedVideoUrl: String) {
        router.openBrowser(this, trimmedVideoUrl)
    }

    override fun downloadFile(trimmedVideoUrl: String, name: String) {
        router.downloadFile(this, trimmedVideoUrl, name)
    }


    //endregion

    //region TrimView.TrimViewCallback

    override fun onPositionChanged(progress: Float) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAddClicked(trimPartInfo: TrimPartInfo, trimView: TrimView) {
        presenter.onAddTrimViewClicked(trimPartInfo)
    }

    override fun onDeleteClicked(trimPartInfo: TrimPartInfo, trimView: TrimView) {
        presenter.onDeleteTrimViewClicked(trimPartInfo)
    }

    //endregion
}
