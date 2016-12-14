package com.github.nekdenis.videoupload.features.editor.logic

import com.cloudinary.Cloudinary
import com.github.nekdenis.videoupload.features.editor.data.TrimPartInfo
import com.github.nekdenis.videoupload.network.api.UploadAPI
import com.github.nekdenis.videoupload.network.api.UploadResponse
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton


@Singleton class UploadModel @Inject constructor(private val api: UploadAPI, private val cloudinary: Cloudinary) {

    //Better move to service for continue upload after activity closed
    fun uploadVideo(pathToVideo: String): Observable<UploadResponse> {
        return api.upload(pathToVideo)
    }

    fun getTrimVideoUrl(videoName: String, trimPartInfoList: List<TrimPartInfo>): String {
        val sb = StringBuilder("http://res.cloudinary.com/${cloudinary.config.cloudName}/video/upload")

        val firstTrimPartInfo = trimPartInfoList[0]
        sb.append("/so_${firstTrimPartInfo.startTimeMSec/1000L},eo_${firstTrimPartInfo.endTimeMSec/1000L}")
        if (trimPartInfoList.size > 1) {
            for (i in 1..trimPartInfoList.size - 1) {

                sb.append("/eo_${trimPartInfoList[i].endTimeMSec/1000L},fl_splice,l_video:$videoName,so_${trimPartInfoList[i].startTimeMSec/1000L}/fl_layer_apply")
            }
        }
        sb.append("/$videoName")
        return sb.toString()
    }

}


