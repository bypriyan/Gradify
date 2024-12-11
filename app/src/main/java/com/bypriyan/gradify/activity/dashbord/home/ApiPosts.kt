package com.bypriyan.gradify.activity.dashbord.home

import com.bypriyan.gradify.apiResponse.ApiResponsePost
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiPosts {
    @GET("posts.php")
    suspend fun getPosts(
        @Query("page") page: Int,
        @Query("user_id") user_id: Int
    ): Response<ApiResponsePost>
}