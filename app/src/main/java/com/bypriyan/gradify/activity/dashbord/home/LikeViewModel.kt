package com.bypriyan.gradify.activity.dashbord.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bypriyan.gradify.apiResponse.ApiResponse
import com.bypriyan.gradify.apiResponse.ApiResponseLike
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikeViewModel @Inject constructor(private val likeRepository: LikeRepository) : ViewModel() {

    private val _likeResponse = MutableLiveData<ApiResponseLike>()
    val likeResponse: LiveData<ApiResponseLike> get() = _likeResponse

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun toggleLike(post_id: Int, student_id: Int) {
        viewModelScope.launch {
            _loading.value = true
            val result = likeRepository.addLike(post_id, student_id)
            _loading.value = false
            when (result) {
                is ApiResponse.Success -> _likeResponse.value = result.data
                is ApiResponse.Error -> {
                    Log.e("LikeViewModel", result.message)
                }
                else -> {}
            }
        }
    }
}
