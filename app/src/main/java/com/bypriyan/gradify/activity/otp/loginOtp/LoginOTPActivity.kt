package com.bypriyan.gradify.activity.otp.loginOtp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bypriyan.bustrackingsystem.utility.Constants
import com.bypriyan.bustrackingsystem.utility.PreferenceManager
import com.bypriyan.gradify.R
import com.bypriyan.gradify.activity.dashbord.MainActivity
import com.bypriyan.gradify.databinding.ActivityLoginBinding
import com.bypriyan.gradify.databinding.ActivityLoginOtpactivityBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginOTPActivity : AppCompatActivity() {


    lateinit var binding: ActivityLoginOtpactivityBinding
    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init
        val otp = intent.getStringExtra("otp")
        val sid = intent.getStringExtra(Constants.KEY_STUDENT_ID)

        binding.submitBtn.setOnClickListener{
            var str:String =  binding.firstPinView.text.toString()
            if(str.length==6 && str.equals(otp)){
                preferenceManager.putString(Constants.KEY_STUDENT_ID, sid!!)
                preferenceManager.putBoolean(Constants.KEY_IS_LOGGED_IN, true)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                Toast.makeText(this, "Invalid OTP", Toast.LENGTH_LONG).show()
            }
        }


    }
}