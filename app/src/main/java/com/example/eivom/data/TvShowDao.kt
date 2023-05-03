package com.example.eivom.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(title: TvShowList)

    @Delete
    suspend fun delete(title: TvShowList)

    @Query("SELECT * FROM TvShowList")
    fun getAllTvShows(): Flow<List<TvShowList>>

    @Query("SELECT * FROM TvShowList WHERE name = :title LIMIT 1")
    fun getTvShowByName(title: String): Flow<TvShowList?>
}