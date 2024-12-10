package com.bypriyan.gradify.activity.dashbord.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bypriyan.gradify.apiResponse.Data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(private val repository: PostRepository) : ViewModel() {
    private val _posts = MutableLiveData<List<Data>>()
    val posts: LiveData<List<Data>> = _posts

    private var currentPage = 1
    private val isLoading = MutableLiveData(false)
    private var allPostsLoaded = false

    fun loadPosts() {
        if (isLoading.value == true || allPostsLoaded) return

        isLoading.value = true
        viewModelScope.launch {
            val result = repository.fetchPosts(currentPage)
            result.onSuccess { newPosts ->
                if (newPosts.isEmpty()) {
                    allPostsLoaded = true // No more posts
                } else {
                    currentPage++
                    _posts.value = (_posts.value ?: emptyList()) + newPosts
                }
            }.onFailure {
                // Handle error (e.g., log or show a Toast)
            }
            isLoading.value = false
        }
    }
}
