package com.bypriyan.gradify.activity.editProfile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bypriyan.gradify.apiResponse.ApiResponse
import com.bypriyan.gradify.apiResponse.ApiUploadImageResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(private val repository: ImageRepository) : ViewModel() {

    private val _uploadState = MutableStateFlow<ApiResponse<ApiUploadImageResponse>>(ApiResponse.Loading)
    val uploadState: StateFlow<ApiResponse<ApiUploadImageResponse>> = _uploadState

    fun uploadImage(image: MultipartBody.Part) {
        viewModelScope.launch {
            _uploadState.value = ApiResponse.Loading
            try {
                val response = repository.uploadImage(image)
                if (response.isSuccessful) {
                    Log.d("UploadState", ": $response")
                    response.body()?.let {
                        _uploadState.value = ApiResponse.Success(it)
                    } ?: run {
                        _uploadState.value = ApiResponse.Error("Unknown error")
                    }
                } else {
                    _uploadState.value = ApiResponse.Error("Upload failed: ${response.message()}")
                }
            } catch (e: Exception) {
                _uploadState.value = ApiResponse.Error(e.localizedMessage ?: "An error occurred", e)
            }
        }
    }
}
