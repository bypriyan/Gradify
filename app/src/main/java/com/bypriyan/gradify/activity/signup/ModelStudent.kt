package com.bypriyan.gradify.activity.signup

data class Student(
    val student_id: Int,
    val admission_no: String,
    val name: String,
    val email: String,
    val phone: String,
    val profile_image: String,
    val teacher_id: Int
)

data class StudentRequest(
    val admission_no: String,
    val name: String,
    val email: String,
    val phone: String,
    val profile_image: String,
    val teacher_id: Int
)

data class SimpleResponse(
    val message: String
)