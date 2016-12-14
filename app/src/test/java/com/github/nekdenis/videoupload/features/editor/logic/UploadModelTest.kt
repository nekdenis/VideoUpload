package com.github.nekdenis.videoupload.features.editor.logic

import com.cloudinary.Cloudinary
import com.github.nekdenis.videoupload.features.editor.data.TrimPartInfo
import com.github.nekdenis.videoupload.network.api.UploadAPI
import com.github.nekdenis.videoupload.network.api.UploadResponse
import io.reactivex.Observable
import junit.framework.Assert
import org.junit.Before
import org.junit.Test

class UploadModelTest {

    lateinit var cloudinary: Cloudinary
    lateinit var model: UploadModel

    @Before
    fun setUp() {
        val config = hashMapOf<String, String>()
        config.put("cloud_name", "dkmnnzi6p")
        config.put("api_key", "key")
        config.put("api_secret", "secret")
        cloudinary = Cloudinary(config)
        model = UploadModel(StubApi(), cloudinary)
    }

    @Test
    fun getTrimVideoUrl() {
        val trimPart1 = TrimPartInfo(0, 10, "")
        val trimPart2 = TrimPartInfo(2, 30, "")
        Assert.assertEquals("http://res.cloudinary.com/dkmnnzi6p/video/upload/so_0,eo_0/eo_0,fl_splice,l_video:cde0rsw93nas8cmuo063,so_0/fl_layer_apply/cde0rsw93nas8cmuo063",
                model.getTrimVideoUrl("cde0rsw93nas8cmuo063", listOf(trimPart1, trimPart2)))
    }

    class StubApi : UploadAPI {
        override fun upload(pathToVideo: String): Observable<UploadResponse> {
            throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

}