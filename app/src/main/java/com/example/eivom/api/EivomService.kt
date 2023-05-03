package com.example.eivom.api

import com.example.eivom.data.EivomListJsonAdapter
import com.example.eivom.data.MovieInfo
import com.squareup.moshi.Moshi
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface EivomService {
    @GET("trending/movie/week")
    suspend fun loadMovieList(
        @Query("api_key") apiKey: String
    ) : Response<MovieInfo>

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        fun create() : EivomService {
            val moshi = Moshi.Builder()
                .add(EivomListJsonAdapter())
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(EivomService::class.java)
        }
    }
}