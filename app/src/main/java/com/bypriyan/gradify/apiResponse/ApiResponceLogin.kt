package com.bypriyan.gradify.apiResponse

data class ApiResponceLogin(
    val message: String,
    val otp: String,
    val status: String,
    val user_id: String,
    val user_type: String
)