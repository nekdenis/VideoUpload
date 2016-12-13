package com.github.nekdenis.videoupload.features.editor

import com.github.nekdenis.videoupload.features.editor.data.TrimPartInfo

interface EditorContract {

    interface View {
        fun showVideo(videoPath: String)
        fun addVideoTrim(videoPath: String, trimPartInfo: TrimPartInfo)
        fun setAsAppliedVideoTrim(trimPartInfo: TrimPartInfo)
        fun removeVideoTrim(trimPartInfo: TrimPartInfo)
        fun showProgress(isVisible: Boolean)
        fun showProceedButton(isVisible: Boolean)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun setVideo(stringExtra: String?)
        fun onAddTrimViewClicked(trimPartInfo: TrimPartInfo)
        fun onDeleteTrimViewClicked(trimPartInfo: TrimPartInfo)
        fun onProceed(list: List<TrimPartInfo>)
    }
}