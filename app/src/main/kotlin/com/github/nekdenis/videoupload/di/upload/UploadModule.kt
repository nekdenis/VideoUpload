package com.github.nekdenis.videoupload.di.upload

import android.content.Context
import com.cloudinary.Cloudinary
import com.github.nekdenis.videoupload.R
import com.github.nekdenis.videoupload.network.api.CloudinaryOldApi
import com.github.nekdenis.videoupload.network.api.UploadAPI
import com.github.nekdenis.videoupload.network.api.UploadRestAPI
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class UploadModule {

    @Provides
    @Singleton
    fun provideCloudinary(context: Context): Cloudinary {
        val config = hashMapOf<String, String>()
        config.put("cloud_name", context.getString(R.string.cloudinary_name))
        config.put("api_key", context.getString(R.string.cloudinary_api_key))
        config.put("api_secret", context.getString(R.string.cloudinary_api_secret))
        return Cloudinary(config)
    }

    @Provides
    @Singleton
    fun provideCloudiariApi(cloudinary: Cloudinary): CloudinaryOldApi {
        return CloudinaryOldApi(cloudinary)
    }

    @Provides
    @Singleton
    fun provideUploadAPI(cloudinaryApi: CloudinaryOldApi): UploadAPI {
        return UploadRestAPI(cloudinaryApi)
    }
}

