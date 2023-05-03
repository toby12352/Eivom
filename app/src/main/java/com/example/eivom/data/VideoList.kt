package com.example.eivom.data

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson
import java.io.Serializable

data class VideoList(
    val key: String,
    val type: String,
    val official: Boolean
): Serializable

@JsonClass(generateAdapter = true)
data class VideoListJson(
    val key: String,
    val type: String,
    val official: Boolean
)

class VideoListJsonAdapter{
    @FromJson
    fun videoInfoFromJson(list: VideoListJson) = VideoList(
        key = list.key,
        type = list.type,
        official = list.official
    )

    @ToJson
    fun videoListToJson(videoList: VideoList) : String{
        throw UnsupportedOperationException("Encoding VideoList to Json is not supported")
    }
}