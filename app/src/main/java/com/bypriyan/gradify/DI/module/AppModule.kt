package com.bypriyan.togocartstore.DI.module

import android.content.Context
import android.content.SharedPreferences
import android.location.Geocoder
import com.bypriyan.bustrackingsystem.utility.PreferenceManager
import com.bypriyan.gradify.activity.dashbord.home.ApiPosts
import com.bypriyan.gradify.activity.login.ApiLogin
import com.bypriyan.gradify.activity.otp.ApiServiceStudent
import com.bypriyan.gradify.activity.signup.ApiOTP
import com.bypriyan.gradify.api.ApiService
import com.bypriyan.gradify.api.ApiServiceLikes
import com.bypriyan.gradify.repositry.ApiServiceMarks
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://bypriyan.com/gradify-api/") // Replace with your base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideLikeApiService(retrofit: Retrofit): ApiServiceLikes =
        retrofit.create(ApiServiceLikes::class.java)

    @Provides
    @Singleton
    fun provideApiPostsService(retrofit: Retrofit): ApiPosts =
        retrofit.create(ApiPosts::class.java)

    @Provides
    @Singleton
    fun provideApiOTPService(retrofit: Retrofit): ApiOTP =
        retrofit.create(ApiOTP::class.java)

    @Provides
    @Singleton
    fun provideApiLoginService(retrofit: Retrofit): ApiLogin =
        retrofit.create(ApiLogin::class.java)

    @Provides
    @Singleton
    fun provideApiServiceStudent(retrofit: Retrofit): ApiServiceStudent =
        retrofit.create(ApiServiceStudent::class.java)

    @Provides
    @Singleton
    fun provideApiServiceMarks(retrofit: Retrofit): ApiServiceMarks =
        retrofit.create(ApiServiceMarks::class.java)


    @Provides
    @Singleton
    fun providePreferenceManager(@ApplicationContext context: Context): PreferenceManager {
        return PreferenceManager(context)
    }
}