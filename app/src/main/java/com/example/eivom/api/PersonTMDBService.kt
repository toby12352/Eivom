package com.example.eivom.api

import com.example.eivom.data.PersonInfo
import com.example.eivom.data.PersonListJsonAdapter
import com.squareup.moshi.Moshi
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PersonTMDBService {
    @GET("person/popular")
    suspend fun loadPersonList(
        @Query("api_key") apikey: String
    ) : Response<PersonInfo>

    companion object{
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        fun create() : PersonTMDBService{
            val moshi = Moshi.Builder()
                .add(PersonListJsonAdapter())
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(PersonTMDBService::class.java)
        }
    }
}