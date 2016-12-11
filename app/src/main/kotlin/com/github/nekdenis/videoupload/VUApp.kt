package com.github.nekdenis.videoupload

import android.app.Application
import com.github.nekdenis.videoupload.di.AppModule
import com.github.nekdenis.videoupload.di.upload.DaggerUploadComponent
import com.github.nekdenis.videoupload.di.upload.UploadComponent

class VUApp : Application() {

    companion object {
        lateinit var uploadComponent: UploadComponent
    }

    override fun onCreate() {
        super.onCreate()
        uploadComponent = DaggerUploadComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}

