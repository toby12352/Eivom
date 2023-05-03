package com.example.eivom.api

import com.example.eivom.data.VideoInfo
import com.example.eivom.data.VideoListJsonAdapter
import com.squareup.moshi.Moshi
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * This is a Retrofit service interface encapsulating communication with the MovieDB API.
 */
interface VideoService {
    @GET("movie/{id}/videos")
    suspend fun loadVideoList(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ) : Response<VideoInfo>

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        fun create() : VideoService {
            val moshi = Moshi.Builder()
                .add(VideoListJsonAdapter())
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(VideoService::class.java)
        }
    }
}