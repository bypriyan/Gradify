package com.bypriyan.gradify.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bypriyan.gradify.model.ApiResponceMarks
import com.bypriyan.gradify.repositry.MarksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarksViewModel @Inject constructor(private val repository: MarksRepository) : ViewModel() {

    private val _marksState = MutableStateFlow<ApiResponceMarks?>(null)
    val marksState: StateFlow<ApiResponceMarks?> get() = _marksState

    fun fetchStudentMarks(studentId: String) {
        viewModelScope.launch {
            _marksState.value = repository.getStudentMarks(studentId)
        }
    }
}