package com.bypriyan.gradify.activity.dashbord.result

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bypriyan.bustrackingsystem.utility.Constants
import com.bypriyan.bustrackingsystem.utility.PreferenceManager
import com.bypriyan.gradify.R
import com.bypriyan.gradify.adapter.AdapterMarks
import com.bypriyan.gradify.databinding.FragmentProfileBinding
import com.bypriyan.gradify.databinding.FragmentResultBinding
import com.bypriyan.gradify.viewmodel.MarksViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private lateinit var marksAdapter: AdapterMarks
    private val marksViewModel: MarksViewModel by viewModels() // ViewModel Injection
    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeMarks()
        fetchMarks()

    }

    private fun setupRecyclerView() {
        binding.marksRv.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeMarks() {
        lifecycleScope.launch {
            marksViewModel.marksState.collect { response ->
                Log.d("sid", "observeMarks: $response")
                response?.let {
                    if (it.status == "success" && it.marks.isNotEmpty()) {
                        marksAdapter = AdapterMarks(requireContext(), it.marks)
                        binding.marksRv.adapter = marksAdapter
                    } else {
                        Toast.makeText(requireContext(), "No marks available", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun fetchMarks() {
        preferenceManager.getString(Constants.KEY_STUDENT_ID)?.let {
            marksViewModel.fetchStudentMarks(it)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}