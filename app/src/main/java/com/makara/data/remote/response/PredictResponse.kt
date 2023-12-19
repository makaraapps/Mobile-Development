package com.makara.data.remote.response

data class PredictionResponse(
    val data: PredictionData?,
    val status: Status
)

data class PredictionData(
    val name: String
)

data class Status(
    val code: Int,
    val message: String
)

data class ErrorResponse(
    val message: String,
    val status: String
)
