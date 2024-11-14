package com.bypriyan.gradify.activity.onBordingScreenActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.bypriyan.bustrackingsystem.utility.Constants
import com.bypriyan.bustrackingsystem.utility.PreferenceManager
import com.bypriyan.gradify.R
import com.bypriyan.gradify.activity.login.LoginActivity
import com.bypriyan.gradify.databinding.ActivityMainBinding
import com.bypriyan.gradify.databinding.ActivityOnBordingScreenBinding
import com.bypriyan.sharemarketcourseinhindi.adapter.AdapterOnBordingScreen
import com.bypriyan.sharemarketcourseinhindi.model.ModelOnBordingScreen
import kotlin.compareTo

class OnBordingScreenActivity : AppCompatActivity() {

    lateinit var binding: ActivityOnBordingScreenBinding

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
                PreferenceManager(this).putBoolean(Constants.KEY_IS_ONBORDING_SCREEN_SEEN, true)
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
            ModelOnBordingScreen("https://cdni.iconscout.com/illustration/premium/thumb/stock-market-trading-6441437-5319167.png?f=webp","शेयर बाजार सीखें", "नए शेयर बाजार निवेशकों के लिए संपूर्ण मार्गदर्शन। शेयर मार्केट की जानकारी से लाभांवित हो और बेहतर निवेश का निर्णय लें।"),
            ModelOnBordingScreen("https://cdni.iconscout.com/illustration/premium/thumb/woman-trading-in-stock-market-4658603-3868031.png","स्टॉक मार्केट में निवेश की राह","अब सीखें शेयर मार्केट के नियम, ट्रिक्स और निवेश के महत्वपूर्ण सिद्धांत इस एप्लिकेशन के माध्यम से। स्वागत है आपका।"),
            ModelOnBordingScreen("https://cdni.iconscout.com/illustration/premium/thumb/person-earning-profits-from-stock-market-4658600-3868028.png","शेयर बाजार में शिक्षा: जैसे जानिए, बनें एक स्मार्ट निवेशक!","यहां आपको शेयर बाजार के महत्वपूर्ण सिद्धांतों की समझ मिलेगी, जो आपको स्मार्ट निवेशक बनाएगी।")

        )
    }

    override fun onStart() {
        super.onStart()
        if(PreferenceManager(this).getBoolean(Constants.KEY_IS_ONBORDING_SCREEN_SEEN)){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

}