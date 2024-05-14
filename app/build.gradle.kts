plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.sdevprem.roadcastassignment"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sdevprem.roadcastassignment"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    val nav_version: String by rootProject.extra
    val lifecycle_version: String by rootProject.extra
    val room_version: String by rootProject.extra
    val hilt_version: String by rootProject.extra
    val coroutines_version: String by rootProject.extra
    val retrofit_version: String by rootProject.extra
    val gson_version: String by rootProject.extra
    val paging_version: String by rootProject.extra
    val glide_version: String by rootProject.extra

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //paging
    implementation("androidx.paging:paging-runtime-ktx:$paging_version")

    // navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    //view-model
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")

    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version")

    //hilt
    implementation("com.google.dagger:hilt-android:${hilt_version}")
    kapt("com.google.dagger:hilt-android-compiler:${hilt_version}")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    //gson
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")

    //glide
    implementation("com.github.bumptech.glide:glide:$glide_version")
}
kapt {
    correctErrorTypes = true
}