package com.bypriyan.gradify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bypriyan.gradify.model.Student
import com.bypriyan.gradify.repositry.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentByIdViewModel @Inject constructor(private val repository: StudentRepository) : ViewModel() {


    private val _student = MutableLiveData<Student?>()
    val student: LiveData<Student?> get() = _student

    fun getStudentById(studentId: Int?) {
        viewModelScope.launch {
            val response = repository.getStudentById(studentId!!)
            response?.let {
                _student.postValue(response)
            }
        }
    }


}
