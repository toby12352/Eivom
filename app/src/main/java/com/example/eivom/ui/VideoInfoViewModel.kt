package com.example.eivom.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eivom.api.VideoService
import com.example.eivom.data.VideoInfo
import com.example.eivom.data.VideoInfoRepository
import kotlinx.coroutines.launch

class VideoInfoViewModel: ViewModel() {
    private val repository = VideoInfoRepository(VideoService.create())

    private val _info = MutableLiveData<VideoInfo?>(null)

    val info: LiveData<VideoInfo?> = _info

    private val _error = MutableLiveData<Throwable?>(null)

    val error: LiveData<Throwable?> = _error

    private val _loading = MutableLiveData<Boolean>(false)

    val loading: LiveData<Boolean> = _loading

    fun loadVideoInfo(id: Int, apiKey: String) {
        /*
         * Launch a new coroutine in which to execute the API call.  The coroutine is tied to the
         * lifecycle of this ViewModel by using `viewModelScope`.
         */
        viewModelScope.launch {
            _loading.value = true
            val result = repository.loadVideoInfo(id, apiKey)
            _loading.value = false
            _error.value = result.exceptionOrNull()
            _info.value = result.getOrNull()
        }
    }
}