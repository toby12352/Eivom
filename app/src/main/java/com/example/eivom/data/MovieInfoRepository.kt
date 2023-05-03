package com.example.eivom.data

import com.example.eivom.api.EivomService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieInfoRepository(
    private val service: EivomService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private var cachedList: MovieInfo? = null

    suspend fun loadMovieInfo(
        apiKey: String
    ) : Result<MovieInfo?> {
        return if(cachedList != null){
            Result.success(cachedList)
        }
        else{
            withContext(ioDispatcher) {
                try {
                    val response = service.loadMovieList(apiKey)
                    if (response.isSuccessful) {
                        cachedList = response.body()
                        Result.success(cachedList)
                    } else {
                        Result.failure(Exception(response.errorBody()?.string()))
                    }
                } catch (e: Exception) {
                    Result.failure(e)
                }
            }

        }
    }
}