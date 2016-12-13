package com.github.nekdenis.videoupload.features.editor.logic

import com.cloudinary.Cloudinary
import com.cloudinary.Transformation
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

    fun getTrimVideoUrl(videoName: String): String {
        return cloudinary.url().transformation(
                Transformation().startOffset("1").endOffset("2")
                        .chain().overlay("video:$videoName").flags("splice").startOffset("1").endOffset("2"))
                .generate(videoName)
    }

}


