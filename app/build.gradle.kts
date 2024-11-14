plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.bypriyan.gradify"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.bypriyan.gradify"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        multiDexEnabled = true
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    //multidex
    implementation ("androidx.annotation:annotation:1.6.0")
    implementation ("org.jetbrains:annotations:24.0.1")
    implementation ("androidx.multidex:multidex:2.0.1")
    //sdp ssp
    implementation ("com.intuit.sdp:sdp-android:1.1.0")
    implementation ("com.intuit.ssp:ssp-android:1.1.0")
    //lottie anim
    implementation ("com.airbnb.android:lottie:6.4.0")
    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    //mvvm
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    // Lifecycles only (without ViewModel or LiveData)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    // LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation ("androidx.core:core-ktx:1.12.0")
    //anim
    implementation ("com.flaviofaria:kenburnsview:1.0.7")
    //coil
    implementation("io.coil-kt.coil3:coil-compose:3.0.2")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.2")
    //splash screen api
    implementation("androidx.core:core-splashscreen:1.0.1")
    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")
    //dot indicator
    implementation("com.tbuonomo:dotsindicator:5.0")
    //viewpager 2
    implementation ("com.google.android.material:material:1.11.0")
    implementation ("androidx.viewpager2:viewpager2:1.0.0")
}
kapt {
    correctErrorTypes = true
}