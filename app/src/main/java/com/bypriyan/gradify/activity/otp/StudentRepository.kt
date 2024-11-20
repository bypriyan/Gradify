package com.bypriyan.gradify.activity.otp

import com.bypriyan.gradify.apiResponse.ApiResponse
import com.bypriyan.gradify.model.Student
import javax.inject.Inject


class StudentRepository @Inject constructor(
    private val apiService: ApiServiceStudent
) {

    suspend fun registerStudent(student: Student): ApiResponse<Student> {
        return try {
            val response = apiService.registerStudent(student)
            if (response.isSuccessful && response.body() != null) {
                ApiResponse.Success(response.body()!!)
            } else {
                ApiResponse.Error(response.message() ?: "Unknown error")
            }
        } catch (e: Exception) {
            ApiResponse.Error("Failed to register student", e)
        }
    }
}
