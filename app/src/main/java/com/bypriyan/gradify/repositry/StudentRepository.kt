package com.bypriyan.gradify.repositry

import android.util.Log
import com.bypriyan.gradify.activity.otp.ApiServiceStudent
import com.bypriyan.gradify.apiResponse.ApiResponseStudent
import com.bypriyan.gradify.model.ApiResponseStudentById
import com.bypriyan.gradify.model.Student
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudentRepository @Inject constructor(private val apiService: ApiServiceStudent) {

    suspend fun getStudentById(studentId: Int): Student? {
        return try {
            val response = apiService.getStudentById(studentId.toString())
            Log.d("sid", "getStudentById: ${response.body()}")
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }

}
