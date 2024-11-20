package com.bypriyan.gradify.model

data class Student(
    val admission_no: String,   // The admission number of the student
    val name: String,           // The student's name
    val email: String,          // The student's email address
    val phone: String,          // The student's phone number
    val profile_image: String,  // The URL or path of the student's profile image
    val teacher_id: String? = null // The ID of the teacher (optional)
)
