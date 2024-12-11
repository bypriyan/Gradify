package com.bypriyan.gradify.activity.dashbord.home

import com.bypriyan.gradify.api.ApiServiceLikes
import com.bypriyan.gradify.apiResponse.ApiResponse
import com.bypriyan.gradify.apiResponse.ApiResponseLike
import com.bypriyan.gradify.apiResponse.ApiResponseStudent
import com.bypriyan.gradify.apiResponse.Data
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LikeRepository @Inject constructor(private val apiService: ApiServiceLikes) {

    suspend fun addLike(post_id: Int, student_id: Int): ApiResponse<ApiResponseLike> {
        return try {
            val response = apiService.registerStudent(student)
            if (response.isSuccessful && response.body() != null) {
                if(response.body()?.status.equals("success")){
                    ApiResponse.Success(response.body()!!)
                }else{
                    ApiResponse.Error(response.body()?.message!!)
                }
            } else {
                ApiResponse.Error(response.message() ?: "Unknown error")
            }
        } catch (e: Exception) {
            ApiResponse.Error("Failed to register student", e)
        }
    }
}