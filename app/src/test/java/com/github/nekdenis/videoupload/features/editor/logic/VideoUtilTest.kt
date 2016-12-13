package com.github.nekdenis.videoupload.features.editor.logic

import org.junit.Assert
import org.junit.Test

class VideoUtilTest {

    @Test
    @Throws(Exception::class)
    fun parseVideoStartStop() {
        Assert.assertEquals("0:00:10/0:01:30", VideoUtil.parseVideoStartStop(0.10f, 0.90f, 100))

        Assert.assertEquals("0:01:12/1:58:48", VideoUtil.parseVideoStartStop(0.01f, 0.99f, (60 * 60 * 2).toLong()))
    }

}