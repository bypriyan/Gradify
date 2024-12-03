package com.bypriyan.gradify.activity.otp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bypriyan.bustrackingsystem.utility.Constants
import com.bypriyan.bustrackingsystem.utility.PreferenceManager
import com.bypriyan.gradify.activity.dashbord.MainActivity
import com.bypriyan.gradify.activity.editProfile.EditProfileActivity
import com.bypriyan.gradify.activity.signup.AuthViewModel
import com.bypriyan.gradify.apiResponse.ApiResponse
import com.bypriyan.gradify.databinding.ActivityOtpactivityBinding
import com.bypriyan.gradify.model.Student
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class OTPActivity : AppCompatActivity() {

    lateinit var binding: ActivityOtpactivityBinding
    private val authViewModel: AuthViewModel by viewModels()
    private val studentViewModel: StudentViewModel by viewModels()
    @Inject
    lateinit var preferenceManager: PreferenceManager
    @Inject
    lateinit var firebaseAuth: FirebaseAuth

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
                registerStudentData(name, email, phoneNumber, admissionNumber, password)
            }else{
                Toast.makeText(this, "Invalid OTP", Toast.LENGTH_LONG).show()
            }
        }

        studentViewModel.studentRegistrationState.observe(this){student->
            Log.d("respo", "onCreate: $student")
        }

        studentViewModel.studentRegistrationState.observe(this) { state ->
            when (state) {
                is ApiResponse.Loading -> {
                    isLoading(true)
                }
                is ApiResponse.Success -> {
                    val student = state.data
                    isLoading(false)
                    preferenceManager.putString(Constants.KEY_STUDENT_ID, student.id.toString())
                    val intent = Intent(this, EditProfileActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                is ApiResponse.Error -> {
                    // Display error message
                    Log.d("lulli", "onCreate: error")
                    isLoading(false)
                    firebaseAuth.signOut()
                }
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

    private fun registerStudentData(name: String?, email: String?, phoneNumber: String?, admissionNumber: String?, password: String?) {
        studentViewModel.registerStudent(Student(admissionNumber!!, name!!, email!!,
            phoneNumber!!, "abc.jpeg"))
    }

}

