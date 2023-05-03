package com.example.eivom.data

import com.example.eivom.api.VideoService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideoInfoRepository(
    private val service: VideoService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private var cachedList: VideoInfo? = null

    suspend fun loadVideoInfo(
        id: Int,
        apiKey: String
    ) : Result<VideoInfo?> {
        return if(cachedList != null){
            Result.success(cachedList)
        } else  {
            withContext(ioDispatcher) {
                try {
                    val response = service.loadVideoList(id,apiKey)
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