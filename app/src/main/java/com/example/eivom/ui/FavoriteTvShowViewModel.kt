package com.example.eivom.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.eivom.data.*
import kotlinx.coroutines.launch

class FavoriteTvShowViewModel(application: Application): AndroidViewModel(application) {
    private val repository = TvShowRepository(
        TvShowDatabase.getInstance(application).tvShowDao()
    )

    val favoriteTvShows = repository.getAllFavoriteTvShows().asLiveData()

    fun addFavoriteTvShow(movie: TvShowList) {
        viewModelScope.launch {
            repository.insertFavoriteTvShow(movie)
        }
    }

    fun removeFavoriteTvShow(movie: TvShowList) {
        viewModelScope.launch {
            repository.deleteFavoriteTvShow(movie)
        }
    }

    fun getTvShowByName(name: String) = repository.getTvShowByName(name).asLiveData()
}