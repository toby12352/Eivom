package com.example.eivom.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoInfo(
    @Json(name = "results") val results: List<VideoList>
)