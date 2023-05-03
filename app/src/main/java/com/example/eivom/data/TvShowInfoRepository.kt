package com.example.eivom.data

import com.example.eivom.api.TvShowTMDBService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TvShowInfoRepository(
    private val service: TvShowTMDBService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private var cachedList: TvShowInfo? = null

    suspend fun loadTvShowInfo(
        apikey: String,
    ) : Result<TvShowInfo?>{
        return if(cachedList != null){
            Result.success(cachedList)
        }
        else{
            withContext(ioDispatcher){
                try{
                    val response = service.loadTvShowList(apikey)
                    if(response.isSuccessful){
                        cachedList = response.body()
                        Result.success(cachedList)
                    }
                    else{
                        Result.failure(Exception(response.errorBody()?.string()))
                    }
                } catch(e: Exception){
                    Result.failure(e)
                }
            }
        }
    }
}