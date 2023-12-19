package com.makara.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makara.data.MakaraRepository
import com.makara.data.local.pref.MakaraModel
import com.makara.data.remote.response.LoginResponse
import com.makara.data.remote.response.RegisterResponse
import com.makara.di.Event
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: MakaraRepository) : ViewModel() {
    val registerResponse: LiveData<RegisterResponse> = repository.signupResponse
    val loginResponse: LiveData<LoginResponse> = repository.loginResponse
    val isLoading: LiveData<Boolean> = repository.isLoading
    val toastText: LiveData<Event<String>> = repository.toastText

    fun postSignup(name: String, email: String, password: String) {
        viewModelScope.launch {
            repository.firebaseSignup(name, email, password)
        }
    }

    fun postLogin(email: String, password: String) {
        viewModelScope.launch {
            repository.firebaseLogin(email, password, viewModelScope)
        }
    }


    fun saveSession(session: MakaraModel) {
        viewModelScope.launch {
            repository.saveSession(session)
        }
    }

    fun login() {
        viewModelScope.launch {
            repository.login()
        }
    }
}