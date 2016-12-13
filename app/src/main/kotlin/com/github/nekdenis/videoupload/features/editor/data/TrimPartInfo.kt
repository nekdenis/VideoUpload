package com.github.nekdenis.videoupload.features.editor.data

data class TrimPartInfo(var startTimeSec: Long, var endTimeSec: Long, val videoPath: String) {

    val id = System.currentTimeMillis()

    override fun equals(other: Any?): Boolean {
        other as TrimPartInfo
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
