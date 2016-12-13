package com.github.nekdenis.videoupload.features.editor.logic

object VideoUtil {

    fun parseVideoStartStop(startProgress: Float, endProgress: Float, duration: Long): String {

        return String.format("%s/%s", parseDuration((duration * startProgress).toLong()), parseDuration((duration * endProgress).toLong()))
    }

    private fun parseDuration(duration: Long): String {
        var durationSec = duration / 1000L
        if ((durationSec % 3600) != 0L) {
            return String.format("%d:%02d:%02d", durationSec / 3600, durationSec % 3600 / 60, durationSec % 60)
        } else {
            return String.format("%02d:%02d", durationSec % 3600 / 60, durationSec % 60)
        }
    }

}
