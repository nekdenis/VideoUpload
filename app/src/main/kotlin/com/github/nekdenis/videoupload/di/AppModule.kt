package com.github.nekdenis.videoupload.di

import android.content.Context
import com.github.nekdenis.videoupload.VUApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: VUApp) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return app;
    }

    @Provides
    @Singleton
    fun provideApplication(): VUApp {
        return app;
    }
}
