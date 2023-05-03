package com.example.eivom.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(title: MovieList)

    @Delete
    suspend fun delete(title: MovieList)

    @Query("SELECT * FROM MovieList")
    fun getAllMovies(): Flow<List<MovieList>>

    @Query("SELECT * FROM MovieList WHERE title = :title LIMIT 1")
    fun getMovieByName(title: String): Flow<MovieList?>
}