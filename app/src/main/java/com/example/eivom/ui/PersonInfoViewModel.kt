package com.example.eivom.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eivom.api.PersonTMDBService
import com.example.eivom.data.PersonInfo
import com.example.eivom.data.PersonInfoRepository
import com.example.eivom.data.PersonList
import kotlinx.coroutines.launch

class PersonInfoViewModel: ViewModel() {
    private val repository = PersonInfoRepository(PersonTMDBService.create())

    private val _info = MutableLiveData<PersonInfo?>(null)

    val info: LiveData<PersonInfo?> = _info

    private val _error = MutableLiveData<Throwable?>(null)

    val error: LiveData<Throwable?> = _error

    private val _loading = MutableLiveData<Boolean>(false)

    val loading: LiveData<Boolean> = _loading

    fun loadPersonInfo(apiKey: String){
        viewModelScope.launch {
            _loading.value = true
            val result = repository.loadPersonInfo(apiKey)
            _loading.value = false
            _error.value = result.exceptionOrNull()
            _info.value = result.getOrNull()
        }
    }
}