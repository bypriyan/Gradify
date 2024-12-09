package com.bypriyan.gradify.activity.editProfile

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bypriyan.bustrackingsystem.utility.Constants
import com.bypriyan.bustrackingsystem.utility.PreferenceManager
import com.bypriyan.gradify.R
import com.bypriyan.gradify.databinding.ActivityEditProfileBinding
import com.bypriyan.gradify.databinding.ActivityOtpactivityBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityEditProfileBinding
    @Inject
    lateinit var preferenceManager: PreferenceManager
    private lateinit var pickImageLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SET DATA
        setData()

        // Initialize the launcher for picking an image
        pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                displayImage(it)
            }
        }

        binding.selectImageCard.setOnClickListener {
            // Launch the image picker
            pickImageLauncher.launch("image/*")
        }

    }

    private fun displayImage(imageUri: Uri) {
        try {
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(contentResolver, imageUri)
                ImageDecoder.decodeBitmap(source)
            } else {
                // Use ContentResolver for older APIs
                contentResolver.openInputStream(imageUri)?.use { inputStream ->
                    android.graphics.BitmapFactory.decodeStream(inputStream)
                }
            }
            binding.profileImage.visibility = View.VISIBLE
            binding.galleryIcon.visibility = View.GONE
            binding.profileImage.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun setData() {
        binding.nameEt.setText(preferenceManager.getString(Constants.KEY_STUDENT_NAME)!!)
        binding.emailEt.setText(preferenceManager.getString(Constants.KEY_STUDENT_EMAIL)!!)
        binding.phoneNumberEt.setText(preferenceManager.getString(Constants.KEY_STUDENT_PHONENUMBER)!!)
        binding.admissionNumbEt.setText(preferenceManager.getString(Constants.KEY_STUDENT_ADMISSION_NUMBER)!!)

    }

    fun isLoading(loading: Boolean){
        if (loading){
            binding.progressbar.visibility = View.VISIBLE
            binding.saveBtn.visibility = View.GONE
        }else {
            binding.progressbar.visibility = View.GONE
            binding.saveBtn.visibility = View.VISIBLE
        }
    }
}