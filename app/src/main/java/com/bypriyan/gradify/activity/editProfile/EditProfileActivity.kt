package com.bypriyan.gradify.activity.editProfile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bypriyan.bustrackingsystem.utility.Constants
import com.bypriyan.bustrackingsystem.utility.PreferenceManager
import com.bypriyan.gradify.R
import com.bypriyan.gradify.apiResponse.ApiResponse
import com.bypriyan.gradify.databinding.ActivityEditProfileBinding
import com.bypriyan.gradify.databinding.ActivityOtpactivityBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private val imageViewModel: ImageViewModel by viewModels()
    private lateinit var pickImageLauncher: ActivityResultLauncher<String>
    private var selectedImageUri: Uri? = null // Store the selected image URI
    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setData()

        pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                selectedImageUri = it // Store the URI for later use
                displayImage(it)
            }
        }

        binding.selectImageCard.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        binding.uploadButton.setOnClickListener {
            selectedImageUri?.let {
                uploadImage(it)
            } ?: run {
                Toast.makeText(this, "Please select an image first", Toast.LENGTH_SHORT).show()
            }
        }

        observeUploadState()
    }

    private fun uploadImage(uri: Uri) {
        lifecycleScope.launch {
            val inputStream = contentResolver.openInputStream(uri)
            inputStream?.let {
                val fileBytes = it.readBytes()
                val requestBody = fileBytes.toRequestBody("image/*".toMediaTypeOrNull())
                val multipartBody = MultipartBody.Part.createFormData(
                    "profile_image",
                    "uploaded_image.jpg",
                    requestBody
                )
                imageViewModel.uploadImage(multipartBody)
            } ?: run {
                Toast.makeText(this@EditProfileActivity, "Failed to read the selected image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayImage(imageUri: Uri) {
        try {
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(contentResolver, imageUri)
                ImageDecoder.decodeBitmap(source)
            } else {
                contentResolver.openInputStream(imageUri)?.use { inputStream ->
                    BitmapFactory.decodeStream(inputStream)
                }
            }
            binding.profileImage.visibility = View.VISIBLE
            binding.galleryIcon.visibility = View.GONE
            binding.profileImage.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun observeUploadState() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                imageViewModel.uploadState.collect { state ->
                    Log.d("UploadState", "observeUploadState: $state")
                    when (state) {
                        is ApiResponse.Loading -> {
                            // Show progress bar if needed
                            isLoading(true)
                        }
                        is ApiResponse.Success -> {
                            // Handle success
                            isLoading(false)
                            preferenceManager.putString(Constants.KEY_STUDENT_PROFILE_IMAGE, state.data.url.toString())
                            Log.d("image", "observeUploadState: ${state.data.url.toString()}")
                        }
                        is ApiResponse.Error -> {
                            // Handle error
                            isLoading(false)
                        }
                        null -> {
                            // Do nothing for idle state
                        }
                    }
                }
            }
        }
    }


    private fun setData() {
        binding.nameEt.setText(preferenceManager.getString(Constants.KEY_STUDENT_NAME) ?: "")
        binding.emailEt.setText(preferenceManager.getString(Constants.KEY_STUDENT_EMAIL) ?: "")
        binding.phoneNumberEt.setText(preferenceManager.getString(Constants.KEY_STUDENT_PHONENUMBER) ?: "")
        binding.admissionNumbEt.setText(preferenceManager.getString(Constants.KEY_STUDENT_ADMISSION_NUMBER) ?: "")
    }

    fun isLoading(loading: Boolean){
        if (loading){
            binding.progressbar.visibility = View.VISIBLE
            binding.uploadButton.visibility = View.GONE
        }else{
            binding.progressbar.visibility = View.GONE
            binding.uploadButton.visibility = View.VISIBLE
        }
    }
}

