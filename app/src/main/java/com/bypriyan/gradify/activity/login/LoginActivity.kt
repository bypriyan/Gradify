package com.bypriyan.gradify.activity.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bypriyan.gradify.activity.dashbord.MainActivity
import com.bypriyan.gradify.activity.editProfile.EditProfileActivity
import com.bypriyan.gradify.activity.signup.SignUpActivity
import com.bypriyan.gradify.databinding.ActivityLoginBinding
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
            startActivity(Intent(this, EditProfileActivity::class.java))
        }
    }
    
}