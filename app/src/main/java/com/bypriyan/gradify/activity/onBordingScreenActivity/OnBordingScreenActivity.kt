package com.bypriyan.gradify.activity.onBordingScreenActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.viewpager2.widget.ViewPager2
import com.bypriyan.bustrackingsystem.utility.Constants
import com.bypriyan.bustrackingsystem.utility.PreferenceManager
import com.bypriyan.gradify.activity.login.LoginActivity
import com.bypriyan.gradify.databinding.ActivityOnBordingScreenBinding
import com.bypriyan.sharemarketcourseinhindi.adapter.AdapterOnBordingScreen
import com.bypriyan.sharemarketcourseinhindi.model.ModelOnBordingScreen
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnBordingScreenActivity : AppCompatActivity() {

    lateinit var binding: ActivityOnBordingScreenBinding
    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityOnBordingScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set adapter to ViewPager2
        var adapter = AdapterOnBordingScreen(this, getListOfOnBordingScreenContent())
        binding.viewPager2.adapter = adapter

        binding.wormDotsIndicator.attachTo(binding.viewPager2)

        binding.viewPager2.isUserInputEnabled = false

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                when(position){
                    0->{
                        binding.previousBtn.visibility = View.GONE
                        binding.nextBtn.visibility = View.VISIBLE
                        binding.nextBtn.text = "NEXT"

                    }
                    1->{
                        binding.previousBtn.visibility = View.VISIBLE
                        binding.nextBtn.visibility = View.VISIBLE
                        binding.nextBtn.text = "NEXT"

                    }
                    2->{
                        binding.previousBtn.visibility = View.VISIBLE
                        binding.nextBtn.text = "Continue"
                    }
                    else ->binding.previousBtn.visibility = View.GONE
                }
            }
        })

        binding.nextBtn.setOnClickListener {
            val nextIndex = binding.viewPager2.currentItem + 1
            if (nextIndex < adapter.itemCount) {
                binding.viewPager2.currentItem = nextIndex
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                preferenceManager.putBoolean(Constants.KEY_IS_ONBORDING_SCREEN_SEEN, true)
                finish()
            }
        }


        binding.previousBtn.setOnClickListener {
            val previousIndex = binding.viewPager2.currentItem - 1
            if (previousIndex >= 0) {
                binding.viewPager2.currentItem = previousIndex
            }
        }

    }

    fun getListOfOnBordingScreenContent():List<ModelOnBordingScreen>{
        return listOf(
            ModelOnBordingScreen("https://cdn2.iconfinder.com/data/icons/latopia-project-development-3d/256/Learning_Code.png","Welcome to Gradify", "Your academic journey made smarter. Stay connected with teachers, track your progress, and engage with classmates—all in one app!"),
            ModelOnBordingScreen("https://cdn2.iconfinder.com/data/icons/latopia-school-education-3d/256/Online_Class.png","Never Miss an Update","Get important notifications and announcements from your teachers, including exam dates, schedules, and event reminders."),
            ModelOnBordingScreen("https://cdn4.iconfinder.com/data/icons/latopia-social-media-3d/256/Social_Media_Analytic.png","Access Your Grades","View your marks for all subjects with ease. Stay informed about your academic progress in real time.")

        )
    }

    override fun onStart() {
        super.onStart()
        if(preferenceManager.getBoolean(Constants.KEY_IS_ONBORDING_SCREEN_SEEN)){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

}