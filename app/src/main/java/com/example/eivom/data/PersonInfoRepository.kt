package com.example.eivom.data

import com.example.eivom.api.PersonTMDBService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PersonInfoRepository(
    private val service: PersonTMDBService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private var cachedList: PersonInfo? = null

    suspend fun loadPersonInfo(
        apiKey: String
    ): Result<PersonInfo?>{
        return if(cachedList != null){
            Result.success(cachedList)
        }
        else{
            withContext(ioDispatcher){
                try{
                    val response = service.loadPersonList(apiKey)
                    if(response.isSuccessful){
                        cachedList = response.body()
                        Result.success(cachedList)
                    }
                    else{
                        Result.failure(Exception(response.errorBody()?.string()))
                    }
                }
                catch(e: Exception){
                    Result.failure(e)
                }
            }
        }
    }
}