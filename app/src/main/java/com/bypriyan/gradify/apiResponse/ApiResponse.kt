package com.bypriyan.gradify.apiResponse


sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val message: String, val cause: Throwable? = null) : ApiResponse<Nothing>()
    object Loading : ApiResponse<Nothing>()
}
