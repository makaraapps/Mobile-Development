package com.makara.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makara.data.MakaraRepository
import com.makara.data.local.pref.MakaraModel
import com.makara.data.local.pref.MakaraPreference
import com.makara.data.remote.response.LoginResponse
import com.makara.data.remote.response.RegisterResponse
import com.makara.di.Event
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: MakaraRepository, private val preferences: MakaraPreference) : ViewModel() {
    val registerResponse: LiveData<RegisterResponse> = repository.signupResponse
    val loginResponse: LiveData<LoginResponse> = repository.loginResponse
    val isLoading: LiveData<Boolean> = repository.isLoading
    val toastText: LiveData<Event<String>> = repository.toastText
    // A MutableLiveData to post the token value which will be private to the ViewModel
    private val _tokenRetrieved = MutableLiveData<String>()

    // An immutable LiveData to expose outside the ViewModel
    val tokenRetrieved: LiveData<String> = _tokenRetrieved

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


    fun getSavedToken() {
        viewModelScope.launch {
            val token = preferences.getAuthToken()
            if (token != null) {
                // Now you have the token, you can use it to save the session or pass it to the UI
                _tokenRetrieved.postValue(token) // LiveData that you can observe in your Activity/Fragment
            } else {
                // Handle the case where the token is null
            }
        }
    }

}