package com.bypriyan.gradify.activity.dashbord.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bypriyan.bustrackingsystem.utility.Constants
import com.bypriyan.bustrackingsystem.utility.PreferenceManager
import com.bypriyan.gradify.R
import com.bypriyan.gradify.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val postsViewModel: PostsViewModel by viewModels()
    private val likeViewModel: LikeViewModel by viewModels()
    private lateinit var adapter: PostAdapter

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val studentId = preferenceManager.getString(Constants.KEY_STUDENT_ID)?.toInt() ?: 0
        adapter = PostAdapter(likeViewModel, studentId)

        setupRecyclerView()
        observeViewModels()
        postsViewModel.loadPosts(studentId)
    }

    private fun setupRecyclerView() {
        binding.postRV.layoutManager = LinearLayoutManager(requireContext())
        binding.postRV.adapter = adapter

        binding.postRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (dy > 0 && layoutManager.findLastVisibleItemPosition() >= adapter.itemCount - 1) {
                    val studentId = preferenceManager.getString(Constants.KEY_STUDENT_ID)?.toInt() ?: 0
                    postsViewModel.loadPosts(studentId)
                }
            }
        })
    }

    private fun observeViewModels() {
        postsViewModel.posts.observe(viewLifecycleOwner) { posts ->
            adapter.updatePosts(posts)
        }

        likeViewModel.likeResponse.observe(viewLifecycleOwner) { response ->
            // Optionally handle the like response
            Log.d("HomeFragment", "Like response: $response")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
