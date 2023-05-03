package com.example.eivom.data

class TvShowRepository(
    private val dao: TvShowDao
) {
    suspend fun insertFavoriteTvShow(name: TvShowList) = dao.insert(name)

    suspend fun deleteFavoriteTvShow(name: TvShowList) = dao.delete(name)

    fun getAllFavoriteTvShows() = dao.getAllTvShows()

    fun getTvShowByName(name: String) = dao.getTvShowByName(name)
}