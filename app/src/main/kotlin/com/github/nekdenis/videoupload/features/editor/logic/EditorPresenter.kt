package com.github.nekdenis.videoupload.features.editor.logic

import com.github.nekdenis.videoupload.VUApp
import com.github.nekdenis.videoupload.features.editor.EditorContract
import com.github.nekdenis.videoupload.features.editor.data.TrimPartInfo
import com.github.nekdenis.videoupload.network.api.UploadResponse
import com.github.nekdenis.videoupload.util.DLog
import com.github.nekdenis.videoupload.util.TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EditorPresenter : EditorContract.Presenter {

    private var view: EditorContract.View? = null

    @Inject lateinit var uploadModel: UploadModel

    init {
        VUApp.uploadComponent.inject(this)
    }

    //region EditorContract.Presenter

    override fun attachView(view: EditorContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun setVideo(videoPath: String?) {
        if (videoPath != null && view != null) {
            DLog.d(TAG, "video path: $videoPath")
            view!!.addVideoTrim(videoPath, TrimPartInfo(0f, 1f, videoPath))
            view!!.showVideo(videoPath)
            uploadVideo(videoPath)
        } else {
            DLog.e(TAG, "video path is null!")
        }
    }

    override fun onDeleteTrimViewClicked(trimPartInfo: TrimPartInfo) {
        view!!.removeVideoTrim(trimPartInfo)
    }

    override fun onAddTrimViewClicked(trimPartInfo: TrimPartInfo) {
        view!!.setAsAppliedVideoTrim(trimPartInfo)
        view!!.addVideoTrim(trimPartInfo.videoPath, TrimPartInfo(0f, 1f, trimPartInfo.videoPath))
    }

    override fun onProceed(list: List<TrimPartInfo>) {
//        view.openInBrowser(
//        uploadModel.getTrimVideoUrl(videoName = list[0].videoPath)
//
    }

    //endregion

    private fun uploadVideo(pathToVideo: String) {
        val subscription = uploadModel.uploadVideo(pathToVideo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            videoUploaded(result)
                        },
                        { e ->
                            DLog.e(TAG, "error: ${e.message}")
                        }
                )
//        disposables.add(subscription)
    }

    private fun videoUploaded(result: UploadResponse) {
        DLog.d(TAG, "result: ${result.responseValues}")
        val trimVideoUrl = uploadModel.getTrimVideoUrl(result.name)
        DLog.d(TAG, "trimmed: $trimVideoUrl")
        view!!.showProgress(false)
        view!!.showProceedButton(true)
    }
}