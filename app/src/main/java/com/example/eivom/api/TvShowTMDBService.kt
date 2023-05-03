package com.example.eivom.api

import com.example.eivom.data.TvShowInfo
import com.example.eivom.data.TvShowListJsonAdapter
import com.squareup.moshi.Moshi
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TvShowTMDBService {
    @GET("trending/tv/week")
    suspend fun loadTvShowList(
        @Query("api_key") apiKey: String
    ) : Response<TvShowInfo>

    companion object{
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        fun create(): TvShowTMDBService{
            val moshi = Moshi.Builder()
                .add(TvShowListJsonAdapter())
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(TvShowTMDBService::class.java)
        }
    }
}