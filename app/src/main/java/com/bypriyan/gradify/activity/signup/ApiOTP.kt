package com.bypriyan.gradify.activity.signup

import com.bypriyan.gradify.apiResponse.ApiResponseOTP
import com.bypriyan.gradify.apiResponse.ApiResponsePost
import com.bypriyan.gradify.model.EmailRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiOTP {
    @POST("otp.php")
    suspend fun getOTP(
        @Body emailRequest: EmailRequest
    ): Response<ApiResponseOTP>
}