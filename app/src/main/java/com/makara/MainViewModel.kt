package com.makara

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.makara.data.MakaraRepository
import com.makara.data.local.pref.MakaraModel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MakaraRepository) : ViewModel() {
    fun getSession(): LiveData<MakaraModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}
