package com.bypriyan.gradify.activity.signup

import com.bypriyan.gradify.apiResponse.ApiResponseOTP
import com.bypriyan.gradify.model.EmailRequest
import javax.inject.Inject

class OTPRepository @Inject constructor(private val apiService: ApiOTP) {

    suspend fun getOTP(email: String): ApiResponseOTP? {
        return try {
            val response = apiService.getOTP(EmailRequest(email))
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
