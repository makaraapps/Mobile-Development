package com.makara.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.gson.Gson
import com.makara.data.local.pref.MakaraModel
import com.makara.data.local.pref.MakaraPreference
import com.makara.data.remote.response.ErrorResponse
import com.makara.data.remote.response.LoginResponse
import com.makara.data.remote.response.RegisterResponse
import com.makara.data.remote.retrofit.ApiService
import com.makara.di.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class MakaraRepository(
    private val apiService: ApiService,
    private val makaraPreference: MakaraPreference
) {
    sealed class PredictionResult {
        data class Success(val foodName: String) : PredictionResult()
        data class Error(val message: String) : PredictionResult()
    }

    private val _signupResponse = MutableLiveData<RegisterResponse>()
    val signupResponse: LiveData<RegisterResponse> = _signupResponse

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

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

    fun firebaseLogin(email: String, password: String, scope: CoroutineScope) {
        _isLoading.value = true
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result?.user?.getIdToken(false)?.addOnSuccessListener { getTokenResult ->
                        val token = getTokenResult.token
                        if (token != null) {
                            // Launch a coroutine within the passed scope to call the suspend function
                            scope.launch {
                                saveToken(token)
                                // After saving the token, set the login response
                                _isLoading.value = false
                                _loginResponse.value = LoginResponse(
                                    error = false,
                                    message = "Login successful",
                                    // ... other data if needed
                                )
                            }
                        }
                    }?.addOnFailureListener { exception ->
                        _isLoading.value = false
                        _toastText.value = Event(getFriendlyErrorMessage(exception))
                    }
                } else {
                    _isLoading.value = false
                    _toastText.value = Event(getFriendlyErrorMessage(task.exception))
                }
            }
    }



    private suspend fun saveToken(token: String){
        makaraPreference.saveAuthToken(token)
    }

    private fun getFriendlyErrorMessage(exception: Exception?): String {
        return when (exception) {
            is FirebaseAuthInvalidCredentialsException -> "The email or password entered is incorrect. Please try again."
            is FirebaseAuthInvalidUserException -> "No account found with this email. Please sign up."
            // Add other cases for different FirebaseAuthException types
            else -> "An unexpected error occurred. Please try again later."
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

    suspend fun sendImageForPrediction(authToken: String, imageUrl: String): PredictionResult {
        _isLoading.value = true
        val json = JSONObject().apply {
            put("image_url", imageUrl)
        }
        val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())

        return try {
            val response = apiService.sendImageForPrediction(authToken, requestBody)
            if (response.isSuccessful) {
                _isLoading.value = false
                // Handle successful response
                val responseBody = response.body() ?: throw Exception("Response body is null")
                PredictionResult.Success(responseBody.data?.name ?: "Unknown")
            } else {
                _isLoading.value = false
                // Handle request errors
                val errorBody = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                PredictionResult.Error(errorResponse.message)
            }
        } catch (e: Exception) {
            _isLoading.value = false
            PredictionResult.Error(e.message ?: "An unknown error occurred")
        }
    }
}