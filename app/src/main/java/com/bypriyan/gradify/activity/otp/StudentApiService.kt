package com.bypriyan.gradify.activity.otp

import com.bypriyan.gradify.apiResponse.ApiResponseStudent
import com.bypriyan.gradify.model.Student
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiServiceStudent {
    @POST("students.php")
    suspend fun registerStudent(@Body student: Student): Response<ApiResponseStudent>
}
