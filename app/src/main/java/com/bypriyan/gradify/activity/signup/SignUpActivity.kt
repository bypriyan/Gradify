package com.bypriyan.gradify.activity.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bypriyan.gradify.R
import com.bypriyan.gradify.activity.otp.OTPActivity
import com.bypriyan.gradify.databinding.ActivityLoginBinding
import com.bypriyan.gradify.databinding.ActivitySignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpBtn.setOnClickListener {
            startActivity(Intent(this, OTPActivity::class.java))
        }

        binding.backBtn.setOnClickListener {
            finish()
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })

        authViewModel.isOTPsent.observe(this) {modelSendOTPResponce ->
            if(modelSendOTPResponce.isSent){
                navigateToOTPActivity(modelSendOTPResponce)
            }
        }

        authViewModel.loading.observe(this){
            isLoading(it)
        }

        binding.signUpBtn.setOnClickListener {
            if(validateInputFields()){
                authViewModel.verifyUserWithPhoneNumber("+91${binding.phoneNumberEt.text.toString()}", this)
            }
        }

    }

    private fun navigateToOTPActivity(modelSendOTPResponse: ModelSendOTPResponce) {
        val name = binding.nameEt.text?.toString()
        val email = binding.emailEt.text?.toString()
        val phoneNumber = binding.phoneNumberEt.text?.toString()
        val admissionNumber = binding.admissionNumbEt.text?.toString()
        val password = binding.passwordEt.text?.toString()

        val intent = Intent(this, OTPActivity::class.java).apply {
            putExtra("name", name)
            putExtra("email", email)
            putExtra("phoneNumber", phoneNumber)
            putExtra("admissionNumber", admissionNumber)
            putExtra("password", password)
            putExtra("verificationId", modelSendOTPResponse.verificationId)
        }
        startActivity(intent)
    }


    private fun validateInputFields(): Boolean {
        val name = binding.nameEt.text?.toString()
        val email = binding.emailEt.text?.toString()
        val phoneNumber = binding.phoneNumberEt.text?.toString()
        val admissionNumber = binding.admissionNumbEt.text?.toString()
        val password = binding.passwordEt.text?.toString()
        val confirmPassword = binding.cnfpasswordEt.text?.toString()

        if (name.isNullOrBlank()) {
            binding.nameEt.error = "Name is required"
            return false
        }

        if (email.isNullOrBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailEt.error = "Invalid email address"
            return false
        }

        if (phoneNumber.isNullOrBlank() || phoneNumber.length != 10 || !phoneNumber.all { it.isDigit() }) {
            binding.phoneNumberEt.error = "Invalid phone number"
            return false
        }

        if (admissionNumber.isNullOrBlank()) {
            binding.admissionNumbEt.error = "Admission number is required"
            return false
        }

        if (password.isNullOrBlank() || password.length < 6) {
            binding.passwordEt.error = "Password must be at least 6 characters"
            return false
        }

        if (confirmPassword.isNullOrBlank() || confirmPassword != password) {
            binding.cnfpasswordInput.error = "Passwords do not match"
            return false
        }

        return true
    }


    fun isLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressbar.visibility = View.VISIBLE
            binding.signUpBtn.visibility = View.GONE
        }else{
            binding.progressbar.visibility = View.GONE
            binding.signUpBtn.visibility = View.VISIBLE
        }
    }

}