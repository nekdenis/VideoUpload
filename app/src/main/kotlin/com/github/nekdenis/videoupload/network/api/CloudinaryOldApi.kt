package com.github.nekdenis.videoupload.network.api

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import io.reactivex.Observable
import javax.inject.Inject

class CloudinaryOldApi @Inject constructor(private val cloudinary: Cloudinary) {

    fun uploadVideo(pathToVideo: String): Observable<UploadResponse> {
        return Observable.defer { Observable.just(uploadVideoInternal(pathToVideo)) }

    }

    private fun uploadVideoInternal(pathToVideo: String): UploadResponse {
        return UploadResponse(cloudinary.uploader().uploadLarge(pathToVideo,
                ObjectUtils.asMap("resource_type", "video")))
    }
}