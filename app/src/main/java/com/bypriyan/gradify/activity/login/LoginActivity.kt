package com.bypriyan.gradify.activity.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bypriyan.bustrackingsystem.utility.Constants
import com.bypriyan.bustrackingsystem.utility.PreferenceManager
import com.bypriyan.gradify.activity.dashbord.MainActivity
import com.bypriyan.gradify.activity.editProfile.EditProfileActivity
import com.bypriyan.gradify.activity.otp.OTPActivity
import com.bypriyan.gradify.activity.otp.loginOtp.LoginOTPActivity
import com.bypriyan.gradify.activity.signup.OTPViewModel
import com.bypriyan.gradify.activity.signup.SignUpActivity
import com.bypriyan.gradify.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    @Inject
    lateinit var preferenceManager: PreferenceManager
    private val viewModel: LoginOTPViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.getOtpBtn.setOnClickListener{
            val email = binding.emailEt.text?.toString()
            Toast.makeText(this, "OTP sent to $email", Toast.LENGTH_LONG).show()
            if (email.isNullOrBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailAddressEt.error = "Invalid email address"
            }else{
                viewModel.sendOTP(email)
            }
        }

        // Observe the StateFlow in a coroutine
        lifecycleScope.launch {
            viewModel.otpState.collect { state ->
                when (state) {
                    is LoginOTPViewModel.LoginOTPState.Idle -> {
                        // Handle idle state
                    }
                    is LoginOTPViewModel.LoginOTPState.Loading -> {
                        // Show loading indicator
                        isLoading(true)
                    }
                    is LoginOTPViewModel.LoginOTPState.Success -> {
                        // Display OTP
                        isLoading(false)
                        navigateToOTPActivity(state.otp, state.userId)
                    }
                    is LoginOTPViewModel.LoginOTPState.Error -> {
                        // Display error message
                        isLoading(false)
                        Toast.makeText(this@LoginActivity, "Error: ${state.error}", Toast.LENGTH_LONG).show()
                    }
                    else -> {}
                }
            }

        }


    }

    private fun navigateToOTPActivity(otp: String, userId: String) {
        val email = binding.emailTv.text?.toString()

        val intent = Intent(this, LoginOTPActivity::class.java).apply {
            putExtra("email", email)
            putExtra("otp", otp)
            putExtra(Constants.KEY_STUDENT_ID,userId)

        }
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        if(preferenceManager.getBoolean(Constants.KEY_IS_LOGGED_IN)){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }

    fun isLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressbar.visibility = View.VISIBLE
            binding.getOtpBtn.visibility = View.GONE
        }else{
            binding.progressbar.visibility = View.GONE
            binding.getOtpBtn.visibility = View.VISIBLE
        }
    }
    
}