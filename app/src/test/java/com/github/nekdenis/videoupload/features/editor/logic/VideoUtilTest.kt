package com.github.nekdenis.videoupload.features.editor.logic

import org.junit.Assert
import org.junit.Test

class VideoUtilTest {

    @Test
    @Throws(Exception::class)
    fun parseVideoStartStop() {
        Assert.assertEquals("00:10/01:30", VideoUtil.parseVideoStartStop(10000L, 90000L))

        Assert.assertEquals("01:12/1:58:48", VideoUtil.parseVideoStartStop(72000L, 7128000L))
    }

}