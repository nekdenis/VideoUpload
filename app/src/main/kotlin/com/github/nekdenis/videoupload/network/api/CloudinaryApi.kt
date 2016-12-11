package com.github.nekdenis.videoupload.network.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CloudinaryApi {

    @GET("/top.json")
    fun upload(@Query("path") path: String): Observable<UploadResponse>
}