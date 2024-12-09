package com.bypriyan.gradify.activity.editProfile

import com.bypriyan.gradify.api.ApiService
import com.bypriyan.gradify.apiResponse.ApiUploadImageResponse
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class ImageRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun uploadImage(image: MultipartBody.Part): Response<ApiUploadImageResponse> {
        return apiService.uploadImage(image)
    }
}