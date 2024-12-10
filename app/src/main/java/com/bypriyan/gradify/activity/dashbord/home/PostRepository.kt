package com.bypriyan.gradify.activity.dashbord.home

import android.util.Log
import com.bypriyan.gradify.apiResponse.ApiResponsePost
import com.bypriyan.gradify.apiResponse.Data
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepository @Inject constructor(private val apiService: ApiPosts) {

    suspend fun fetchPosts(page: Int): Result<List<Data>> {
        return try {
            val response = apiService.getPosts(page)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()?.data ?: emptyList())
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

