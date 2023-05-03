package com.example.eivom.data

class FavoriteMovieRepository(
    private val dao: MovieDao
) {
    suspend fun insertFavoriteMovie(movie: MovieList) = dao.insert(movie)

    suspend fun deleteFavoriteMovie(movie: MovieList) = dao.delete(movie)

    fun getAllFavoriteMovies() = dao.getAllMovies()

    fun getMovieByName(name: String) = dao.getMovieByName(name)
}