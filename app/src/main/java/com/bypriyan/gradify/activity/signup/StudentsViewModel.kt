package com.bypriyan.gradify.activity.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentsViewModel @Inject constructor(private val repository: SignUpRepositry) : ViewModel() {

    private val _students = MutableStateFlow<List<Student>>(emptyList())
    val students: StateFlow<List<Student>> get() = _students

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    private val _operationMessage = MutableStateFlow<String?>(null)
    val operationMessage: StateFlow<String?> get() = _operationMessage

    // Fetch all students
    fun fetchStudents() {
        viewModelScope.launch {
            try {
                val response = repository.getAllStudents()
                if (response.status == "success") {
                    _students.value = response.data ?: emptyList()
                } else {
                    _error.value = response.message
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    // Create a new student
    fun createStudent(student: StudentRequest) {
        viewModelScope.launch {
            try {
                val response = repository.createStudent(student)
                if (response.status == "success") {
                    _operationMessage.value = response.message
                    fetchStudents() // Refresh the list
                } else {
                    _error.value = response.message
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    // Update an existing student
    fun updateStudent(id: Int, student: StudentRequest) {
        viewModelScope.launch {
            try {
                val response = repository.updateStudent(id, student)
                if (response.status == "success") {
                    _operationMessage.value = response.message
                    fetchStudents() // Refresh the list
                } else {
                    _error.value = response.message
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    // Delete a student
    fun deleteStudent(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.deleteStudent(id)
                if (response.status == "success") {
                    _operationMessage.value = response.message
                    fetchStudents() // Refresh the list
                } else {
                    _error.value = response.message
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
