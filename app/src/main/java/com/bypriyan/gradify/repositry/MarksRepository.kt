package com.bypriyan.gradify.repositry

import com.bypriyan.gradify.model.ApiResponceMarks
import javax.inject.Inject

class MarksRepository @Inject constructor(private val apiService: ApiServiceMarks) {

    suspend fun getStudentMarks(studentId: String): ApiResponceMarks? {
        return try {
            val response = apiService.getStudentById(studentId)
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