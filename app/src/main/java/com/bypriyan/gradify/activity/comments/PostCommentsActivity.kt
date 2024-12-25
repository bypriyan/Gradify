package com.bypriyan.gradify.activity.comments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bypriyan.gradify.R
import com.bypriyan.gradify.databinding.ActivityPostCommentsBinding
import com.bypriyan.gradify.databinding.ActivitySignUpBinding

class PostCommentsActivity : AppCompatActivity() {
    lateinit var binding: ActivityPostCommentsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}