package com.bypriyan.gradify.activity.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bypriyan.gradify.activity.signup.OTPRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginOTPViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {

    private val _otpState = MutableStateFlow<LoginOTPState>(LoginOTPState.Idle)
    val otpState: StateFlow<LoginOTPState> get() = _otpState

    fun sendOTP(email: String) {
        viewModelScope.launch {
            _otpState.value = LoginOTPState.Loading
            val response = repository.getLoginOTP(email)
            if (response != null && response.status == "success") {
                _otpState.value = LoginOTPState.Success(response.otp, response.user_id)
            } else {
                _otpState.value = LoginOTPState.Error(response?.message ?: "Unknown error")
            }
        }
    }

    sealed class LoginOTPState {
        object Idle : LoginOTPState()
        object Loading : LoginOTPState()
        data class Success(val otp: String, val userId: String) : LoginOTPState()
        data class Error(val error: String) : LoginOTPState()
    }
}