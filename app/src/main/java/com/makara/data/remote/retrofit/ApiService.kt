package com.makara.data.remote.retrofit

import com.makara.data.remote.response.LoginResponse
import com.makara.data.remote.response.PredictionResponse
import com.makara.data.remote.response.RegisterResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*



interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<LoginResponse>

    @POST("predict")
    suspend fun sendImageForPrediction(
        @Header("Authorization") token: String,
        @Body body: RequestBody
    ): Response<PredictionResponse>
}