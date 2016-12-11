package com.github.nekdenis.videoupload.network.api

import io.reactivex.Observable

interface UploadAPI {
    fun upload(pathToVideo: String): Observable<UploadResponse>
}

