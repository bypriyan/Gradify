package com.bypriyan.gradify.activity.dashbord

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.bypriyan.bustrackingsystem.utility.Constants
import com.bypriyan.bustrackingsystem.utility.PreferenceManager
import com.bypriyan.gradify.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import com.bypriyan.gradify.R
import com.bypriyan.gradify.activity.otp.StudentViewModel
import com.bypriyan.gradify.viewmodel.StudentByIdViewModel
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var navController: NavController
    private val studentViewModel: StudentViewModel by viewModels()
    @Inject
    lateinit var preferenceManager: PreferenceManager

    private val studentViewModelById: StudentByIdViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        navController = Navigation.findNavController(this, R.id.frameLayout);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        Log.d("sid", "onCreate: ${preferenceManager.getString(Constants.KEY_STUDENT_ID)}")

        preferenceManager.getString(Constants.KEY_STUDENT_ID)?.let {
            studentViewModelById.getStudentById(it.toInt())
        }

        studentViewModelById.student.observe(this) { students ->
            // Handle student list
            Log.d("sid", "onCreate: ${students}")
            preferenceManager.putString(Constants.KEY_STUDENT_NAME, students?.name)
            preferenceManager.putString(Constants.KEY_STUDENT_EMAIL, students?.email)
            preferenceManager.putString(Constants.KEY_STUDENT_PHONENUMBER, students?.phone)
            preferenceManager.putString(Constants.KEY_STUDENT_PROFILE_IMAGE, students?.profile_image)
            preferenceManager.putString(Constants.KEY_STUDENT_ADMISSION_NUMBER, students?.admission_no)
        }

    }
}