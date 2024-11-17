package com.bypriyan.gradify.activity.signup

import javax.inject.Inject

class SignUpRepositry @Inject constructor(private val api: StudentsApi)  {

    suspend fun getAllStudents() = api.getAllStudents()

    suspend fun getStudent(id: Int) = api.getStudent(id)

    suspend fun createStudent(student: StudentRequest) = api.createStudent(student)

    suspend fun updateStudent(id: Int, student: StudentRequest) = api.updateStudent(id, student)

    suspend fun deleteStudent(id: Int) = api.deleteStudent(id)

}