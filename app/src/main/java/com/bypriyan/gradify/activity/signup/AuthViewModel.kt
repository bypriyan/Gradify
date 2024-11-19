package com.bypriyan.gradify.activity.signup

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val firebaseRepo: FirebaseRepo
) :ViewModel() {

    //is otp sent
    private val _isOTPsent = MutableLiveData<ModelSendOTPResponce>()
    val isOTPsent: LiveData<ModelSendOTPResponce> = _isOTPsent

    //is otp correct
    private val _isOTPCorrect = MutableLiveData<Boolean>()
    val isOTPCorrect: LiveData<Boolean> = _isOTPCorrect

    //loading
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> get() = _loading


    fun verifyUserWithPhoneNumber(phoneNumber: String, activity: Activity){
        _loading.postValue(true)
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                firebaseRepo.sendOTP(phoneNumber, activity, object : FirebaseRepo.OTPCallback{
                    override fun onOTPResult(isSent: Boolean, verificationId: String?) {
                        if (isSent) {
                            // OTP sent successfully
                            if (verificationId != null) {
                                _isOTPsent.postValue(ModelSendOTPResponce(isSent, verificationId, phoneNumber))
                                _loading.postValue(false)
                            }
                        } else {
                            // Failed to send OTP
                            _isOTPsent.postValue(ModelSendOTPResponce(isSent = false, null, null))
                            _loading.postValue(false)
                        }
                    }

                })
            }
        }

    }// fun end

    fun verifyOTP(verificationId: String, code:String){
        _loading.postValue(true)
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                firebaseRepo.checkOTP(verificationId, code, object : FirebaseRepo.OTPVerificationCallback{
                    override fun isOTPCorrect(isCorrect: Boolean) {
                        _isOTPCorrect.postValue(isCorrect)
                    }
                })
            }
        }
    }//end fun

    fun loadingStatus(status: Boolean){
        _loading.postValue(status)
    }



}

data class ModelSendOTPResponce(
    val isSent: Boolean,
    val verificationId: String? = null,
    val phoneNumber: String? = null
)