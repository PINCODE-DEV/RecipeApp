plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.plugin.kotlin.android)
    alias(libs.plugins.plugin.hilt)
    id("kotlin-kapt")
}

android {
    namespace = "com.softanime.recipeapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.softanime.recipeapp"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
    }
    buildFeatures {
        viewBinding = true
    }
    kapt {
        correctErrorTypes = true
        useBuildCache = true
    }

}

dependencies {
    implementation(libs.androidx.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    //test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Lifecycle
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    //Dynamic Sizes
    implementation(libs.dynamic.sizes)
    // Coil
    implementation(libs.coil)
    // Calligraphy
    implementation(libs.calligraphy3)
    implementation(libs.viewpump)
    // Dagger Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    //OkHTTP client
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    //Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    // Gson
    implementation(libs.gson)
    // Shimmer
    implementation(libs.shimmer)
    implementation(libs.shimmer.recyclerview)
    // Lottie
    implementation(libs.lottie)
}