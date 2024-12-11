package com.bypriyan.gradify.api

import com.bypriyan.gradify.apiResponse.ApiResponseLike
import com.bypriyan.gradify.apiResponse.ApiResponseStudent
import com.bypriyan.gradify.model.ModelLike
import com.bypriyan.gradify.model.Student
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServiceLikes {
    @POST("like_api.php")
    suspend fun addLike(@Body modelLike: ModelLike): Response<ApiResponseLike>
}