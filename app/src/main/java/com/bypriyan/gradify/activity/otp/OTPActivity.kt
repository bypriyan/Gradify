package com.bypriyan.gradify.activity.otp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bypriyan.gradify.MainActivity
import com.bypriyan.gradify.R
import com.bypriyan.gradify.activity.signup.AuthViewModel
import com.bypriyan.gradify.databinding.ActivityOtpactivityBinding
import com.bypriyan.gradify.databinding.ActivitySignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class OTPActivity : AppCompatActivity() {

    lateinit var binding: ActivityOtpactivityBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init
        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val phoneNumber = intent.getStringExtra("phoneNumber")
        val admissionNumber = intent.getStringExtra("admissionNumber")
        val password = intent.getStringExtra("password")
        val verificationId = intent.getStringExtra("verificationId")

        binding.SendCodeBtn.setOnClickListener{
            var str:String =  binding.firstPinView.text.toString()
            if(str.length==6){
                authViewModel.verifyOTP(verificationId!!, str)
            }else{
                Toast.makeText(this, "Invalid OTP", Toast.LENGTH_LONG).show()
            }
        }

        authViewModel.loading.observe(this){
            isLoading(it)
        }

        authViewModel.isOTPCorrect.observe(this){
            if(it){
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                Toast.makeText(this, "Invalid OTP", Toast.LENGTH_LONG).show()
            }
        }

    }

    fun isLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressbar.visibility = View.VISIBLE
            binding.SendCodeBtn.visibility = View.GONE
        }else{
            binding.progressbar.visibility = View.GONE
            binding.SendCodeBtn.visibility = View.VISIBLE
        }
    }

}
