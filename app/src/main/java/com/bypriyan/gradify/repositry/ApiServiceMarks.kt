package com.bypriyan.gradify.repositry

import com.bypriyan.gradify.model.ApiResponceMarks
import com.bypriyan.gradify.model.Student
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceMarks {

    @GET("get_marks.php")
    suspend fun getStudentById(@Query("student_id") studentId: String): Response<ApiResponceMarks>

}