package com.bypriyan.gradify.activity.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OTPViewModel @Inject constructor(private val repository: OTPRepository) : ViewModel() {

    private val _otpState = MutableStateFlow<OTPState>(OTPState.Idle)
    val otpState: StateFlow<OTPState> get() = _otpState

    fun sendOTP(email: String) {
        viewModelScope.launch {
            _otpState.value = OTPState.Loading
            val response = repository.getOTP(email)
            if (response != null && response.status == "success") {
                _otpState.value = OTPState.Success(response.otp)
            } else {
                _otpState.value = OTPState.Error(response?.message ?: "Unknown error")
            }
        }
    }

    sealed class OTPState {
        object Idle : OTPState()
        object Loading : OTPState()
        data class Success(val otp: String) : OTPState()
        data class Error(val error: String) : OTPState()
    }
}