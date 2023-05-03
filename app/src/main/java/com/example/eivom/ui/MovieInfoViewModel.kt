package com.example.eivom.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eivom.api.EivomService
import com.example.eivom.data.MovieInfo
import com.example.eivom.data.MovieInfoRepository
import kotlinx.coroutines.launch

class MovieInfoViewModel: ViewModel()  {
    private val repository = MovieInfoRepository(EivomService.create())

    private val _info = MutableLiveData<MovieInfo?>(null)

    val info: LiveData<MovieInfo?> = _info

    private val _error = MutableLiveData<Throwable?>(null)

    val error: LiveData<Throwable?> = _error

    private val _loading = MutableLiveData<Boolean>(false)

    val loading: LiveData<Boolean> = _loading

    fun loadMovieInfo(apiKey: String) {
        /*
         * Launch a new coroutine in which to execute the API call.  The coroutine is tied to the
         * lifecycle of this ViewModel by using `viewModelScope`.
         */
        viewModelScope.launch {
            _loading.value = true
            val result = repository.loadMovieInfo(apiKey)
            _loading.value = false
            _error.value = result.exceptionOrNull()
            _info.value = result.getOrNull()
        }
    }
}