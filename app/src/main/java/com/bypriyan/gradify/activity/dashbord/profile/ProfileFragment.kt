package com.bypriyan.gradify.activity.dashbord.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil3.load
import coil3.request.CachePolicy
import coil3.request.crossfade
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bypriyan.bustrackingsystem.utility.Constants
import com.bypriyan.bustrackingsystem.utility.PreferenceManager
import com.bypriyan.gradify.R
import com.bypriyan.gradify.activity.editProfile.EditProfileActivity
import com.bypriyan.gradify.databinding.FragmentHomeBinding
import com.bypriyan.gradify.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()

        binding.editBtn.setOnClickListener{
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }

        preferenceManager.getString(Constants.KEY_STUDENT_PROFILE_IMAGE)?.let {
            Log.d("loading", "onCreate: ${Constants.KEY_BASE_URL +"profileImg"+ it}")
            Glide.with(binding.profileImage.context)
                .load(Constants.KEY_BASE_URL +"profileImg/"+ it)
                .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache the image
                .placeholder(R.drawable.app_logo) // Add a placeholder image
                .into(binding.profileImage)

            binding.galleryIcon.visibility = View.GONE
            binding.profileImage.visibility = View.VISIBLE

        }

    }

    private fun setData() {
        binding.nameEt.setText(preferenceManager.getString(Constants.KEY_STUDENT_NAME) ?: "")
        binding.emailEt.setText(preferenceManager.getString(Constants.KEY_STUDENT_EMAIL) ?: "")
        binding.phoneNumberEt.setText(preferenceManager.getString(Constants.KEY_STUDENT_PHONENUMBER) ?: "")
        binding.admissionNumbEt.setText(preferenceManager.getString(Constants.KEY_STUDENT_ADMISSION_NUMBER) ?: "")
    }

}