package com.bypriyan.gradify.model

data class Mark(
    val created_at: String,
    val marks: String,
    val semester: String,
    val subject_id: String,
    val test_type: String
)