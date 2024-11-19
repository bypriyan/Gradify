package com.bypriyan.gradify.activity.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil3.load
import coil3.request.crossfade
import com.bypriyan.bustrackingsystem.utility.Constants
import com.bypriyan.bustrackingsystem.utility.PreferenceManager
import com.bypriyan.gradify.MainActivity
import com.bypriyan.gradify.R
import com.bypriyan.gradify.activity.signup.SignUpActivity
import com.bypriyan.gradify.databinding.ActivityLoginBinding
import com.bypriyan.gradify.databinding.ActivityOnBordingScreenBinding
import com.bypriyan.gradify.utility.AESHelper
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }


    }

    override fun onStart() {
        super.onStart()
        firebaseAuth.currentUser?.let {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    
}