package com.bypriyan.gradify.apiResponse

data class ApiResponsePost(
    val `data`: List<Data>,
    val pagination: Pagination,
    val status: String
)