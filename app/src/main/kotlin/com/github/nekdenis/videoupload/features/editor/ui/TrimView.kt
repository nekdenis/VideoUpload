package com.github.nekdenis.videoupload.features.editor.ui

import android.content.Context
import android.media.MediaMetadataRetriever
import android.view.View
import android.widget.RelativeLayout
import com.github.nekdenis.videoupload.R
import com.github.nekdenis.videoupload.features.editor.data.TrimPartInfo
import com.github.nekdenis.videoupload.features.editor.logic.VideoUtil
import com.github.nekdenis.videoupload.features.editor.ui.VideoTimelineView.VideoTimelineViewDelegate
import kotlinx.android.synthetic.main.view_trim.view.*

class TrimView(context: Context) : RelativeLayout(context), VideoTimelineViewDelegate {

    private var videoLength = 0L
    private var trimPartInfo: TrimPartInfo? = null
    var trimViewCallback: TrimViewCallback? = null

    init {
        View.inflate(getContext(), R.layout.view_trim, this)
    }

    fun initVideo(pathToVideo: String, trimPartInfo: TrimPartInfo) {
        this.trimPartInfo = trimPartInfo
        videoTimelineView.setVideoPath(pathToVideo)
        videoTimelineView.setDelegate(this)
        obtainVideoDuration(pathToVideo)
    }

    fun setAsCurrent() {
        videoTimelineView.isEnabled = true
        timelineButton.setText(R.string.action_add_trim)
        timelineButton.setOnClickListener {
            trimViewCallback?.onAddClicked(trimPartInfo!!, this)
        }
    }

    fun setAsApplied() {
        videoTimelineView.isEnabled = false
        timelineButton.setText(R.string.action_remove_trim)
        timelineButton.setOnClickListener {
            trimViewCallback?.onDeleteClicked(trimPartInfo!!, this)
        }
    }

    private fun obtainVideoDuration(pathToVideo: String) {
        val mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(pathToVideo)
        val duration = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        videoLength = java.lang.Long.parseLong(duration)
        trimPartInfo!!.endTimeMSec = videoLength
        updateTime()
    }

    private fun updateTime() {
        if (trimPartInfo != null) {
            timelineTime.text = VideoUtil.parseVideoStartStop(trimPartInfo!!.startTimeMSec, trimPartInfo!!.endTimeMSec)
        }
    }

    //region VideoTimelineViewDelegate
    override fun onLeftProgressChanged(progress: Float) {
        trimPartInfo!!.startTimeMSec = (progress * videoLength).toLong()
        updateTime()
    }

    override fun onRightProgressChanged(progress: Float) {
        trimPartInfo!!.endTimeMSec = ((progress * videoLength).toLong())
        updateTime()
    }

//endregion

    interface TrimViewCallback {
        fun onPositionChanged(progress: Float)
        fun onAddClicked(trimPartInfo: TrimPartInfo, trimView: TrimView)
        fun onDeleteClicked(trimPartInfo: TrimPartInfo, trimView: TrimView)
    }
}

