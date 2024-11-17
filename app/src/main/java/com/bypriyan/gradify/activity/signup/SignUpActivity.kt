package com.bypriyan.gradify.activity.signup

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bypriyan.gradify.R
import com.bypriyan.gradify.databinding.ActivityLoginBinding
import com.bypriyan.gradify.databinding.ActivitySignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding
    private val viewModel: StudentsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Option 1: Using LiveData
        viewModel.operationMessageLiveData.observe(this) { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.createStudent(StudentRequest("1234567890",
            "Priyan",
            "harrison@store.com",
            "1234567890",
            "1234567890",
            0))


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