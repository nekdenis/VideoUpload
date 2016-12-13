package com.github.nekdenis.videoupload.di.upload

import com.github.nekdenis.videoupload.di.AppModule
import com.github.nekdenis.videoupload.di.NetworkModule
import com.github.nekdenis.videoupload.features.editor.logic.EditorPresenter
import com.github.nekdenis.videoupload.features.editor.ui.EditorActivity
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
    fun inject(editorPresenter: EditorPresenter)

}

