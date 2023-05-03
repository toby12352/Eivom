package com.example.eivom.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eivom.api.TvShowTMDBService
import com.example.eivom.data.TvShowInfo
import com.example.eivom.data.TvShowInfoRepository
import kotlinx.coroutines.launch

class TvShowViewModel : ViewModel() {
    private val repository = TvShowInfoRepository(TvShowTMDBService.create())

    private val _info = MutableLiveData<TvShowInfo?>(null)

    val info: LiveData<TvShowInfo?> = _info

    private val _error = MutableLiveData<Throwable?>(null)

    val error: LiveData<Throwable?> = _error

    private val _loading = MutableLiveData<Boolean>(false)

    val loading: LiveData<Boolean> = _loading

    fun loadTvShowInfo(apikey: String){
        viewModelScope.launch {
            _loading.value = true
            val result = repository.loadTvShowInfo(apikey)
            _loading.value = false
            _error.value = result.exceptionOrNull()
            _info.value = result.getOrNull()
        }
    }
}