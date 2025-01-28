package com.bypriyan.gradify.activity.login

import com.bypriyan.gradify.apiResponse.ApiResponceLogin
import com.bypriyan.gradify.apiResponse.ApiResponseOTP
import com.bypriyan.gradify.model.EmailRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiLogin {

    @POST("login.php")
    suspend fun getLoginOTP(
        @Body emailRequest: EmailRequest
    ): Response<ApiResponceLogin>


}