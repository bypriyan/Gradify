package com.bypriyan.gradify.activity.login

import com.bypriyan.gradify.activity.signup.ApiOTP
import com.bypriyan.gradify.apiResponse.ApiResponceLogin
import com.bypriyan.gradify.apiResponse.ApiResponseOTP
import com.bypriyan.gradify.model.EmailRequest
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiService: ApiLogin) {

    suspend fun getLoginOTP(email: String): ApiResponceLogin? {
        return try {
            val response = apiService.getLoginOTP(EmailRequest(email))
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}
