package com.bypriyan.gradify.api

import com.bypriyan.gradify.apiResponse.ApiUploadImageResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("uploadImage.php")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): Response<ApiUploadImageResponse>
}