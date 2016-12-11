package com.github.nekdenis.videoupload.di.upload

import com.github.nekdenis.videoupload.EditorActivity
import com.github.nekdenis.videoupload.di.AppModule
import com.github.nekdenis.videoupload.di.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        UploadModule::class,
        NetworkModule::class)
)

interface UploadComponent {

    fun inject(editorActivity: EditorActivity)

}

