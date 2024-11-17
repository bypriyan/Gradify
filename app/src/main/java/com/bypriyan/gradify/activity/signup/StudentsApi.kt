package com.bypriyan.gradify.activity.signup

import com.bypriyan.gradify.model.ApiResponse
import retrofit2.http.*

interface StudentsApi {

    @GET("students.php")
    suspend fun getAllStudents(): ApiResponse<List<Student>>

    @GET("students.php")
    suspend fun getStudent(@Query("id") id: Int): ApiResponse<Student>

    @POST("students.php")
    suspend fun createStudent(@Body student: StudentRequest): ApiResponse<SimpleResponse>

    @PUT("students.php")
    suspend fun updateStudent(
        @Query("id") id: Int,
        @Body student: StudentRequest
    ): ApiResponse<SimpleResponse>

    @DELETE("students.php")
    suspend fun deleteStudent(@Query("id") id: Int): ApiResponse<SimpleResponse>
}
