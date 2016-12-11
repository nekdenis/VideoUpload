package com.github.nekdenis.videoupload.network.api

import io.reactivex.Observable
import javax.inject.Inject

//
//class UploadRestAPI @Inject constructor(private val cloudiaryApi: CloudinaryApi) : UploadAPI {
//
//    override fun upload(after: String, limit: String): Observable<UploadResponse> {
//        return cloudiaryApi.getTop(after, limit)
//    }
//}

class UploadRestAPI @Inject constructor(private val cloudinaryApi: CloudinaryOldApi) : UploadAPI {

    override fun upload(pathToVideo: String): Observable<UploadResponse> {
        return cloudinaryApi.uploadVideo(pathToVideo)
    }
}