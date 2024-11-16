package com.bypriyan.gradify.api

import com.bypriyan.sharemarketcourseinhindi.model.ModelOnBordingScreen
import com.google.android.gms.common.api.Response
import retrofit2.http.GET

interface ApiService {
    @GET("endpoint")
    suspend fun getSomeData(): Any
}