package com.bypriyan.gradify.apiResponse

data class Data(
    val content: String,
    val created_at: String,
    val images: List<String>,
    val like_count: String,
    val post_id: String,
    val teacher_email: String,
    val teacher_id: String,
    val teacher_name: String,
    val teacher_profile_image: String,
    val title: String,
    val user_liked: Boolean
)