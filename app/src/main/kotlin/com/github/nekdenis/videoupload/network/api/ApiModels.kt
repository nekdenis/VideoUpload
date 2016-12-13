package com.github.nekdenis.videoupload.network.api


//{resource_type=video, frame_rate=24.75, etag=0b9b35843559622959f4f062341b7e9a, signature=e2a991dac8bc3a85a92b0f530d0115511cf605d9,
// audio={channels=1, codec=amr_nb, channel_layout=mono, bit_rate=12800, frequency=8000},
// bit_rate=1306991,
// url=http://res.cloudinary.com/dkmnnzi6p/video/upload/v1481456807/pjvfoxtb0fkynnybpnh4.mp4,
// video={profile=Baseline, pix_format=yuv420p, codec=h264, dar=4:3, bit_rate=77887, level=31},
// height=320,
// secure_url=https://res.cloudinary.com/dkmnnzi6p/video/upload/v1481456807/pjvfoxtb0fkynnybpnh4.mp4,
// pages=0, duration=2.666, format=mp4, public_id=pjvfoxtb0fkynnybpnh4, rotation=90, version=1481456807,
// original_filename=file, width=240, created_at=2016-12-11T11:46:47Z, is_audio=false, tags=[], bytes=435555, type=upload}
class UploadResponse(val responseValues: Map<Any?, Any?>) {
    val url = responseValues["url"] as String
    val name = responseValues["public_id"] as String + "." + responseValues["format"]
}

