package com.makara.data

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.makara.data.local.pref.MakaraModel
import com.makara.data.local.pref.MakaraPreference
import com.makara.data.remote.response.LoginResponse
import com.makara.data.remote.response.RegisterResponse
import com.makara.data.remote.retrofit.ApiService
import com.makara.di.Event
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MakaraRepository(
    private val apiService: ApiService,
    private val makaraPreference: MakaraPreference
) {

    private val _signupResponse = MutableLiveData<RegisterResponse>()
    val signupResponse: LiveData<RegisterResponse> = _signupResponse

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

//    fun postSignup(name: String, email: String, password: String) {
//        _isLoading.value = true
//        val client = apiService.register(name,email, password)
//        client.enqueue(object : Callback<RegisterResponse> {
//            override fun onResponse(
//                call: Call<RegisterResponse>,
//                response: Response<RegisterResponse>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful && response.body() != null) {
//                    _signupResponse.value = response.body()
//                    _toastText.value = Event(response.body()?.message.toString())
//                } else {
//                    _toastText.value = Event(response.message().toString())
//                    Log.e(TAG, "onFailure: ${response.message()}, ${response.body()?.message.toString()}")
//                }
//            }
//
//            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
//                _isLoading.value = false
//                _toastText.value = Event(t.message.toString())
//                Log.e(TAG, "onFailure: ${t.message.toString()}")
//            }
//        })
//    }

    fun firebaseSignup(name: String, email: String, password: String) {
        _isLoading.value = true
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    // Registration successful
                    val firebaseUser = task.result?.user
                    _signupResponse.value = RegisterResponse(
                        error = false,
                        message = "Signup successful",
                        // ... other data you might want to include
                    )
                    // Optional: Update the display name of the Firebase User
                    val profileUpdates = userProfileChangeRequest {
                        displayName = name
                    }
                    firebaseUser?.updateProfile(profileUpdates)?.addOnCompleteListener { profileTask ->
                        if (profileTask.isSuccessful) {
                            Log.d(TAG, "User profile updated.")
                        }
                    }
                } else {
                    // Handle failed signup
                    _toastText.value = Event(task.exception?.message.toString())
                }
            }
    }


//    fun postLogin(email: String, password: String) {
//        _isLoading.value = true
//        val client = apiService.login(email, password)
//        client.enqueue(object : Callback<LoginResponse> {
//            override fun onResponse(
//                call: Call<LoginResponse>,
//                response: Response<LoginResponse>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful && response.body() != null) {
//                    _loginResponse.value = response.body()
//                    _toastText.value = Event(response.body()?.message.toString())
//                } else {
//                    _toastText.value = Event(response.message().toString() + ", begow dalem")
//                    Log.e(
//                        TAG,
//                        "onFailure: ${response.message()}, ${response.body()?.message.toString()}, begowdalem"
//                    )
//                }
//            }
//
//            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                _isLoading.value = false
//                _toastText.value = Event(t.message.toString() + ", begow")
//                Log.e(TAG, "onFailure: ${t.message.toString()}, begow")
//            }
//        })
//    }

    fun firebaseLogin(email: String, password: String) {
        Log.e(TAG, "login: firebes")
        _isLoading.value = true
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    // Login successful
                    val firebaseUser = task.result?.user
                    _loginResponse.value = LoginResponse(
                        error = false,
                        message = "Login successful",
                        // ... other data you might want to include
                    )
                } else {
                    // Handle failed login
//                    _toastText.value = Event("ah masa sihhh")
                    _toastText.value = Event(task.exception?.message.toString())
                }
            }
    }


    suspend fun saveSession(user: MakaraModel) {
        makaraPreference.saveSession(user)
    }

    fun getSession(): Flow<MakaraModel> {
        return makaraPreference.getSession()
    }

    suspend fun login() {
        makaraPreference.login()
    }

    suspend fun logout() {
        makaraPreference.logout()
    }

    companion object {
        private const val TAG = "MakaraRepository"
    }
}