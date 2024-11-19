package com.bypriyan.gradify.activity.signup

import android.app.Activity
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FirebaseRepo @Inject constructor(val firebaseAuth: FirebaseAuth) {

    interface OTPCallback{
        fun onOTPResult(isSent: Boolean, verificationId:String?)
    }

    interface OTPVerificationCallback{
        fun isOTPCorrect(isCorrect: Boolean)
    }

    suspend fun sendOTP(phoneNumber: String, activity: Activity, callback: OTPCallback) {
        Log.d("TAG", "authenticatePhoneNumber: ${Thread.currentThread().name}")
        val mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                Log.d(
                    "TAG",
                    "onVerificationCompleted: this is on verification complete ${p0.smsCode.toString()} " + Thread.currentThread().name
                )
                callback.onOTPResult(true, null)
            }

            override fun onVerificationFailed(p0: FirebaseException) {

                Log.d(
                    "TAG",
                    "onVerificationFailed: this is on verification failed ${p0.message}" + Thread.currentThread().name
                )
                callback.onOTPResult(false, null)
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                Log.d(
                    "TAG",
                    "onVerificationCompleted: this is on verification complete ${p0}     " + Thread.currentThread().name
                )
                // Update the verification ID
                callback.onOTPResult(true, p0)
            }
        }

        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity)
            .setCallbacks(mCallbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    suspend fun checkOTP(verificationId: String, code:String, otpVerificationCallback: OTPVerificationCallback){
        firebaseAuth.signInWithCredential(PhoneAuthProvider.getCredential(verificationId, code))
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    otpVerificationCallback.isOTPCorrect(true)
                } else {
                    otpVerificationCallback.isOTPCorrect(false)
                }
            }
    }
}
