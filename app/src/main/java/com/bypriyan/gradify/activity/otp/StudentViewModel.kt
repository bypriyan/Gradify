package com.bypriyan.gradify.activity.otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bypriyan.gradify.apiResponse.ApiResponse
import com.bypriyan.gradify.model.Student
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentViewModel @Inject constructor(
    private val repository: StudentRepository
) : ViewModel() {

    private val _studentRegistrationState = MutableLiveData<ApiResponse<Student>>()
    val studentRegistrationState: LiveData<ApiResponse<Student>> get() = _studentRegistrationState

    /**
     * Registers a student by calling the repository method.
     */
    fun registerStudent(student: Student) {
        viewModelScope.launch {
            _studentRegistrationState.value = ApiResponse.Loading
            try {
                val response = repository.registerStudent(student)
                _studentRegistrationState.value = response
            } catch (e: Exception) {
                _studentRegistrationState.value = ApiResponse.Error("Failed to register student", e)
            }
        }
    }
}
